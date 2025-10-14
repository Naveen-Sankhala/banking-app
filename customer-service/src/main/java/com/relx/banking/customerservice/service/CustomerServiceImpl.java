package com.relx.banking.customerservice.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.relx.banking.commondto.BranchDetailsDto;
import com.relx.banking.commondto.CityDto;
import com.relx.banking.commondto.MasCurrencyDto;
import com.relx.banking.commondto.StateDto;
import com.relx.banking.customerservice.client.AccountClient;
import com.relx.banking.customerservice.client.BankConfigApi;
import com.relx.banking.customerservice.client.CityAndStateDetailsClient;
import com.relx.banking.customerservice.dao.ICustomerDao;
import com.relx.banking.customerservice.dto.CustomerAddressDto;
import com.relx.banking.customerservice.dto.CustomerDetailsDto;
import com.relx.banking.customerservice.dto.CustomerDetailsRecord;
import com.relx.banking.customerservice.dto.CustomerRequestDto;
import com.relx.banking.customerservice.dto.CustomerResponseDto;
import com.relx.banking.customerservice.dto.JointAccountDto;
import com.relx.banking.customerservice.entity.Address;
import com.relx.banking.customerservice.entity.Customer;
import com.relx.banking.customerservice.entity.CustomerDetails;
import com.relx.banking.customerservice.enums.AddressType;
import com.relx.banking.customerservice.enums.GenderEnum;
import com.relx.banking.customerservice.mappers.AddressMapper;
import com.relx.banking.customerservice.mappers.CustomerDetailsMapper;
import com.relx.banking.customerservice.mappers.CustomerMapper;
import com.relx.banking.customerservice.util.CustomerUtil;
import com.relx.banking.customerservice.util.NotFoundException;
import com.relx.banking.customerservice.util.ObjectMapperUtils;
import com.relx.banking.customerservice.util.exceptionhandling.InvalidCustomerException;

/**
 * @author Naveen Sankhala
 */
@Service
public class CustomerServiceImpl implements ICustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private ICustomerDao iCustomerDao;
	
	private AccountClient accountClient;
	private BankConfigApi bankConfigApi;
	private CityAndStateDetailsClient cityStateDetailsClient;
	
	@Override
	public String createNewCustomer(CustomerRequestDto customerRequest) {
		List<Address> addressList = new ArrayList<Address>();
		
		Customer isCustomerExist = iCustomerDao.findCustomerByAadharAndPanNo(customerRequest.getAadharNumber(), customerRequest.getPanNumber());
				if (isCustomerExist !=null) {
					throw new InvalidCustomerException("Customer already exist !!!");
				}

		Customer customerMapper = CustomerMapper.INSTANCE.customerDTOToCustomer(customerRequest);
		CustomerDetails customerDetails = CustomerDetailsMapper.INSTANCE.customerDetailsDTOToCustomerDetails(customerRequest.getCustomerDetails());
		
		if(customerRequest.getAddress() !=null && !customerRequest.getAddress().isEmpty()) {
			for(CustomerAddressDto custAddressDto: customerRequest.getAddress()) {
				Address addressMapper = AddressMapper.INSTANCE.customerAddressDTOToCustomerAddress(custAddressDto);
				addressMapper.setCustomer(customerMapper);
				addressList.add(addressMapper);
			}
		}
		
		customerMapper.setCustomerDetails(customerDetails);
		customerMapper.setAddress(addressList);
		customerMapper.setStatus("Active");
		Customer customer = iCustomerDao.saveCustomer(customerMapper);

		return customer.getCustomerIdentificationNo();
	}
	
	
	@Override
	public List<String> createBulkNewCustomer(List<CustomerRequestDto> customerReqDto) {
		
		customerReqDto.stream().forEach(records->{
			String cifNo = createNewCustomer(records);
		});
		return null;
	}

	@Override
	public List<String> importAndCreateNewCustomer(MultipartFile custDetailsFile) {
		List<String> cifNo = new ArrayList<String>();
		List<String> validateCustomer = new ArrayList<String>();
		List<String> existCustomer = new ArrayList<String>();
		boolean success=false;

		//Normalize the path by suppressing sequence like "path/..." and inner simple dots.
		String fileName= StringUtils.cleanPath(custDetailsFile.getOriginalFilename());
		logger.info("Stat Collection file name :: "+fileName);
		
		List<CustomerRequestDto> customerReqDto = importCustDetailFromExcel(custDetailsFile,existCustomer,validateCustomer);

		return null;
	}

	private List<CustomerRequestDto> importCustDetailFromExcel(MultipartFile file,List<String> existCustomer, List<String> validateCustomer){
		
		List<CustomerRequestDto> custReqDtoList = new ArrayList<CustomerRequestDto>();
		List<CustomerAddressDto> addressDtoList = new ArrayList<CustomerAddressDto>();
		CustomerRequestDto custReqDto = null;
		CustomerDetailsDto customerDetailsDto = null;
		CustomerAddressDto addressDto= null;
		
		try (InputStream inputStream = file.getInputStream();
				XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
			Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {

				Row row = rowIterator.next();
				if(row.getRowNum() ==0 )
					continue;
				
				custReqDto = new CustomerRequestDto();
				customerDetailsDto = new CustomerDetailsDto();
				BranchDetailsDto branch =null;
				
				StateDto state = getAllState(1L).stream()
					    .filter(st -> st.getStateName() != null && st.getStateName().toLowerCase().contains(row.getCell(11).getStringCellValue()))
					    .findFirst()
					    .orElse(null);
				
				CityDto city =  getAllCity(state.getStateId())
						.stream()
					    .filter(ci -> ci.getCityName() != null && ci.getCityName().toLowerCase().contains(row.getCell(11).getStringCellValue()))
					    .findFirst()
					    .orElse(null);;
				
				
				if(row.getCell(0).getStringCellValue()== null) {
					branch= getBranchDetailsByBranchCode(row.getCell(0).getStringCellValue());
					customerDetailsDto.setBranchId(branch.getBranchId());
				}else {
					customerDetailsDto.setBranchId(branch.getBranchId());
				}
				
				custReqDto.setCustTitle(row.getCell(1).getStringCellValue());
				
				if(row.getCell(2).getStringCellValue() == null)
					logger.error("Name Cann't be blank");
				else	
					custReqDto.setFirstName(row.getCell(2).getStringCellValue());
				
				custReqDto.setMiddleName(row.getCell(3).getStringCellValue() == null ?  "" : row.getCell(3).getStringCellValue());
				custReqDto.setLastName(row.getCell(4).getStringCellValue() == null ?  "" : row.getCell(4).getStringCellValue());
				
				if(row.getCell(5).getStringCellValue() == null)
					logger.error("Gender Cann't be blank");
				else	
					custReqDto.setGender(row.getCell(5).getStringCellValue());
				
				if(row.getCell(6).getStringCellValue() == null)
					logger.error("Marital Status Cann't be blank");
				else	
					customerDetailsDto.setMaritalStatus(row.getCell(6).getStringCellValue());
				
				if(row.getCell(7).getStringCellValue() == null)
					logger.error("Relation Type Cann't be blank");
				else	
					customerDetailsDto.setRelationType(row.getCell(7).getStringCellValue());
				
				if(row.getCell(8).getStringCellValue() == null)
					logger.error("Husband/Father Title Cann't be blank");
				else	
					customerDetailsDto.setHusbandFatherTitle(row.getCell(8).getStringCellValue());
				
				if(row.getCell(9).getStringCellValue() == null)
					logger.error("Husband/Father Name Cann't be blank");
				else	
					customerDetailsDto.setHusbandFatherName(row.getCell(9).getStringCellValue());
				
				if(row.getCell(10).getStringCellValue() != null) {                                
					customerDetailsDto.setMotherTitle(null);
					customerDetailsDto.setMotherRelation(null); 
					customerDetailsDto.setMotherName(row.getCell(10).getStringCellValue());                                 
				}
				
				if(row.getCell(11).getStringCellValue() == null) {
					logger.error("DOB Cann't be blank");
				}else {	
					custReqDto.setDob(CustomerUtil.stringToLocalDate(row.getCell(11).getStringCellValue()));
					customerDetailsDto.setMajorDate(CustomerUtil.stringToLocalDate(row.getCell(11).getStringCellValue())); 
				}
				
				if(row.getCell(12).getStringCellValue() == null)
					logger.error("Aadhar Number Cann't be blank");
				else	
					custReqDto.setAadharNumber( row.getCell(12).getStringCellValue());
				
				if(row.getCell(13).getStringCellValue() == null)
					logger.error("Pan Number Cann't be blank");
				else	
					custReqDto.setPanNumber( row.getCell(13).getStringCellValue());
				
				
				customerDetailsDto.setPassportNumber(row.getCell(14).getStringCellValue() == null ?  null : row.getCell(14).getStringCellValue());
				customerDetailsDto.setPassportPlaceIssue(row.getCell(15).getStringCellValue() == null ?  null : row.getCell(15).getStringCellValue());
				customerDetailsDto.setPassportIssueDate(row.getCell(16).getStringCellValue() == null ?  null : CustomerUtil.stringToLocalDate(row.getCell(16).getStringCellValue()));
				customerDetailsDto.setPassportExpiryDate(row.getCell(17).getStringCellValue() == null ? null : CustomerUtil.stringToLocalDate(row.getCell(17).getStringCellValue()));
				
				
				if(row.getCell(18).getStringCellValue() == null)
					logger.error("Contact No/ Mobile No Cann't be blank");
				else	
					custReqDto.setContactNo( row.getCell(18).getStringCellValue());
				
				custReqDto.setAlternateContactNo(row.getCell(19).getStringCellValue() == null ? null :row.getCell(19).getStringCellValue());
				
				custReqDto.setEmailId(row.getCell(20).getStringCellValue() == null ? null :row.getCell(20).getStringCellValue());
				
				customerDetailsDto.setIsMinor(row.getCell(21).getStringCellValue() == null ?  'N' : CustomerUtil.stringToCharcter(row.getCell(21).getStringCellValue()));
				customerDetailsDto.setHasNominee(row.getCell(22).getStringCellValue() == null ?  'N' : CustomerUtil.stringToCharcter(row.getCell(22).getStringCellValue()));
				customerDetailsDto.setHasGuardian(row.getCell(23).getStringCellValue() == null ?  'N' : CustomerUtil.stringToCharcter(row.getCell(23).getStringCellValue()));
				customerDetailsDto.setGuardianType(row.getCell(24).getStringCellValue() == null ?  null : row.getCell(24).getStringCellValue());
				customerDetailsDto.setNumDependents(row.getCell(25).getStringCellValue() == null ?  0 : Integer.valueOf(row.getCell(25).getStringCellValue()));
				// category code
				//customerDetailsDto.setGuardianType(row.getCell(26).getStringCellValue() == null ?  null : row.getCell(26).getStringCellValue());
				
				customerDetailsDto.setConstitutionId(null);//27
				customerDetailsDto.setReligionId(null);//28
				customerDetailsDto.setCasteId(null);//29
				customerDetailsDto.setOccupationId(null);//30
				customerDetailsDto.setEducationQual(null);//31
				
				MasCurrencyDto currency = getMasCurrency(1L);
				customerDetailsDto.setCurrency_Id(currency.getCurrencyId());   //32
				
				// Introduction type //33
				//customerDetailsDto.setGuardianType(row.getCell(33).getStringCellValue() == null ?  null : row.getCell(33).getStringCellValue());

				customerDetailsDto.setChecksumValue(null);                              
				customerDetailsDto.setNationalIdNumber(null);                           
				
			                                  
                customerDetailsDto.setMembershipNumber(null);                           
				customerDetailsDto.setEmployeeNumber(null);                             
				customerDetailsDto.setAccountManager(null);                             
				customerDetailsDto.setCustomerGroup(null);                              
				
				                      
				if(row.getCell(34).getStringCellValue() !=null &&AddressType.valueOf(row.getCell(34).getStringCellValue()).equals("PERMANENT")) {
					addressDto = new CustomerAddressDto();
					addressDto.setAddressType(AddressType.valueOf(row.getCell(34).getStringCellValue()));
					addressDto.setHouseNumber(row.getCell(35).getStringCellValue() == null ?  "" : row.getCell(35).getStringCellValue());
					addressDto.setAddressLine1(row.getCell(36).getStringCellValue() == null ?  "" : row.getCell(36).getStringCellValue());
					addressDto.setAddressLine2(row.getCell(37).getStringCellValue() == null ?  "" : row.getCell(37).getStringCellValue());
					addressDto.setAddressLine3(row.getCell(38).getStringCellValue() == null ?  "" : row.getCell(38).getStringCellValue());
					addressDto.setStreet(row.getCell(39).getStringCellValue() == null ?  "" : row.getCell(39).getStringCellValue());
					addressDto.setCityId(city.getCityId().intValue());//40
					addressDto.setStateId(state.getStateId().intValue());//45
					addressDto.setZipcode(row.getCell(1).getStringCellValue());//46
					addressDtoList.add(addressDto);
				}
				if(row.getCell(47).getStringCellValue() !=null && (AddressType.valueOf(row.getCell(47).getStringCellValue()).equals("LOCAL")
						|| AddressType.valueOf(row.getCell(47).getStringCellValue()).equals("BUSINESS"))) {
					addressDto = new CustomerAddressDto();
					addressDto.setAddressType(AddressType.valueOf(row.getCell(48).getStringCellValue()));
					addressDto.setHouseNumber(row.getCell(49).getStringCellValue() == null ?  "" : row.getCell(49).getStringCellValue());
					addressDto.setAddressLine1(row.getCell(50).getStringCellValue() == null ?  "" : row.getCell(50).getStringCellValue());
					addressDto.setAddressLine2(row.getCell(51).getStringCellValue() == null ?  "" : row.getCell(51).getStringCellValue());
					addressDto.setAddressLine3(row.getCell(52).getStringCellValue() == null ?  "" : row.getCell(52).getStringCellValue());
					addressDto.setStreet(row.getCell(53).getStringCellValue() == null ?  "" : row.getCell(53).getStringCellValue());
					addressDto.setCityId(city.getCityId().intValue());//54
					addressDto.setStateId(state.getStateId().intValue());//55
					addressDto.setZipcode(row.getCell(1).getStringCellValue());//56
					addressDtoList.add(addressDto);
				}
				
				
				custReqDto.setCustomerDetails(customerDetailsDto);
				custReqDto.setAddress(addressDtoList);
				
				custReqDtoList.add(custReqDto);
				
			}
		} catch (IOException e) {
			logger.error("Getting Error On importing Customer Details on this Row :: " + 1 +" Exception is :: "+e.getMessage());	}
		return null;
	}


	@Override
	public CustomerResponseDto getCustomerDetails(String cifNo) {
		Customer customer = iCustomerDao.getCustomerDetails(cifNo,"Active");
		CustomerResponseDto custResDto = ObjectMapperUtils.map(customer, CustomerResponseDto.class);
		custResDto.setCifNo(customer.getCustomerIdentificationNo());
		return custResDto;
	}

	@Override
	public CustomerResponseDto getCustomerDetails(Long customerId) {
		Customer customer = iCustomerDao.getCustomerDetails(customerId,"Active");
		CustomerResponseDto custResDto = ObjectMapperUtils.map(customer, CustomerResponseDto.class);
		custResDto.setCifNo(customer.getCustomerIdentificationNo());
		return custResDto;
	}
	
	@Override
	public boolean updateCustomerMetaData(CustomerRequestDto custReqDto) {
		Customer customer = iCustomerDao.getCustomerDetails(custReqDto.getCifNo(),"Active");
		List<Address> addressList = new ArrayList<Address>();
		List<Address> existAddressList = customer.getAddress();
		
		if(custReqDto.getAddress() !=null && !custReqDto.getAddress().isEmpty()) {
			for(CustomerAddressDto addressDto: custReqDto.getAddress()) {
				
				Address address  = existAddressList.stream()
						.filter(add-> add.getAddressId() == addressDto.getAddressId())
						.findFirst().orElse(Address.builder().build());
				

				addressList.add(Address.builder()
						.addressId(address.getAddressId())	
						.addressType(addressDto.getAddressType())
						.houseNumber(addressDto.getHouseNumber())
						.addressLine1(addressDto.getAddressLine1())
						.addressLine2(addressDto.getAddressLine2())
						.addressLine3(addressDto.getAddressLine3())
						.street(addressDto.getStreet())
						.cityId(addressDto.getCityId())
						.stateId(addressDto.getStateId())
						.zipcode(addressDto.getZipcode())
						.customer(customer)
						.build());
			}
		}
		
		Customer customerBuilder = Customer.builder()
				.customerId(customer.getCustomerId())
				.customerIdentificationNo(customer.getCustomerIdentificationNo())
				.firstName(custReqDto.getFirstName())
				.middleName(custReqDto.getMiddleName())
				.lastName(custReqDto.getLastName())
				.gender(GenderEnum.fromString(custReqDto.getGender()))
				.dob(custReqDto.getDob())
				.aadharNumber(custReqDto.getAadharNumber())
				.panNumber(custReqDto.getPanNumber())
				.contactNo(custReqDto.getContactNo())
				.alternateContactNo(custReqDto.getAlternateContactNo())
				.emailId(custReqDto.getEmailId())
				.address(addressList)
				.createdBy(custReqDto.getCreatedBy())
				.createdDate(customer.getCreatedDate())
				.lastChgBy(custReqDto.getLastChgBy())
				.build();
		boolean updated = iCustomerDao.saveCustomer(customerBuilder) != null;
		
		return updated;
	}

	@Override
	public boolean activeDeactiveAccount(String cifNo) {
		Customer customer = iCustomerDao.getCustomerDetails(cifNo,"InActive");
		if(customer == null)
			throw new NotFoundException("There is no Inactive Customer Found ::"+cifNo);
		
		customer.setStatus("Active");
		Boolean active = iCustomerDao.saveCustomer(customer) != null;
		return active;
	}

	@Override
	public List<CustomerAddressDto> getAddresListOfCustomer(String cifNo) {
		Customer customer = iCustomerDao.getCustomerDetails(cifNo,"InActive");
		List<Address> addressList = iCustomerDao.getAddresListOfCustomer(customer.getCustomerId());
		List<CustomerAddressDto> custAddressdto = ObjectMapperUtils.mapAll(addressList, CustomerAddressDto.class);
		return custAddressdto;
	}
	
	@Override
	public Map<String, Object> getAllCustomer(String custName, int page, int size){
		return iCustomerDao.getAllCustomer(custName, page, size);
	}
	
	
	public CustomerDetailsRecord getCustomerWithAccounts(Long customerId) {
        Customer customer = iCustomerDao.getCustomerDetails(customerId,"Active");
       
        // Fetch accounts from account-service
        List<JointAccountDto> accounts = accountClient.getAccountsByCustomerId(customerId);

        return new CustomerDetailsRecord(
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmailId(),
                accounts
        );
    }

	@Override
	public boolean verifyCustomer(String cifNo) {
		Customer customer = iCustomerDao.getCustomerDetails(cifNo,"Active");
		if(customer !=null)
			return true;
		return false;
	}


	public void getCityNameAndStateNameDetails(String stateName, String cityName) {
		cityStateDetailsClient.getCityNameAndStateNameDetails(stateName, cityName);
	}
	
//	@Cacheable("bankDate")
//    public LocalDate getBankDate(Long bankId) {
//        String url = configServiceUrl + "/api/config/bank-date/" + bankId;
//        return restTemplate.getForObject(url, LocalDate.class);
//    }

	
//	@Scheduled(fixedDelay = 60000) // every 1 min refresh
//    public void refreshBankDate() {
//        cacheManager.getCache("bankDate").clear();
//    }

	public BranchDetailsDto getBranchDetailsByBranchCode(String branchCode) {
		return bankConfigApi.getBranchDetails(null, branchCode);
	}
	
	public BranchDetailsDto getBranchDetailsByBranchId(Long branchId) {
		return bankConfigApi.getBranchDetails(branchId, null);
	}
	
	public MasCurrencyDto getMasCurrency(Long countryId) {
		return bankConfigApi.getCurrency(countryId);
	}
	
	private List<StateDto> getAllState(long countryId) {
		return bankConfigApi.getAllState(null);
	}
	
	private List<CityDto> getAllCity(long stateId) {
		return bankConfigApi.getAllCity(stateId);
	}


	
}
