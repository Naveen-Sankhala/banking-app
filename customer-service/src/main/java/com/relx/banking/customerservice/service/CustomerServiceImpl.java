package com.relx.banking.customerservice.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.relx.banking.commondto.CastDto;
import com.relx.banking.commondto.CityDto;
import com.relx.banking.commondto.CustomerConstitutionDto;
import com.relx.banking.commondto.MasCurrencyDto;
import com.relx.banking.commondto.OccupationDto;
import com.relx.banking.commondto.ReligionDto;
import com.relx.banking.commondto.StateDto;
import com.relx.banking.commonrecord.BranchDetailsRecord;
import com.relx.banking.customerservice.client.AccountClient;
import com.relx.banking.customerservice.client.BankConfigApi;
import com.relx.banking.customerservice.client.BankConfigClient;
import com.relx.banking.customerservice.config.BankConfigurationHolder;
import com.relx.banking.customerservice.dao.ICustomerDao;
import com.relx.banking.customerservice.dto.BulkCustomerSaveResponse;
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
import com.relx.banking.customerservice.util.ObjectMapperUtils;
import com.relx.banking.customerservice.util.exceptionhandling.InvalidCustomerException;
import com.relx.banking.customerservice.util.exceptionhandling.NotFoundException;

/**
 * @author Naveen Sankhala
 */
@Service
public class CustomerServiceImpl implements ICustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private BankConfigClient bankConfigClient;
	
	@Autowired
	private ICustomerDao iCustomerDao;
	
	@Autowired
	private AccountClient accountClient;
	
	@Autowired
	private BankConfigApi bankConfigApi;
	
	@Autowired
	private BankConfigurationHolder configHolder;
	
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
		customerDetails.setCustomer(customerMapper);
		customerMapper.setCustomerDetails(customerDetails);
		customerMapper.setAddress(addressList);
		customerMapper.setStatus("Active");
		Customer customer = iCustomerDao.saveCustomer(customerMapper);

		return customer.getCustomerIdentificationNo();
	}
	
	public List<String> mapCustDetailsAndSave(List<CustomerRequestDto> customerReqList) {
		List<String> cifNoList= new ArrayList<String>();

		for(CustomerRequestDto customerRequest : customerReqList) {
			List<Address> addressList = new ArrayList<Address>();

			Customer customerMapper = CustomerMapper.INSTANCE.customerDTOToCustomer(customerRequest);
			CustomerDetails customerDetails = CustomerDetailsMapper.INSTANCE.customerDetailsDTOToCustomerDetails(customerRequest.getCustomerDetails());
			customerDetails.setCustomer(customerMapper);
			
			if(customerRequest.getAddress() !=null && !customerRequest.getAddress().isEmpty()) {
				for(CustomerAddressDto custAddressDto: customerRequest.getAddress()) {
					Address addressMapper = AddressMapper.INSTANCE.customerAddressDTOToCustomerAddress(custAddressDto);
					addressMapper.setCustomer(customerMapper);
					addressList.add(addressMapper);
				}
			}

			customerMapper.setCustomerDetails(customerDetails);
			customerMapper.setAddress(addressList);
			customerMapper.setDateOfCreated(configHolder.getBankDate());
			customerMapper.setCreatedDate(configHolder.getBankDateTime());
			customerMapper.setStatus("Active");
			
			ObjectMapperUtils.converJavaObjectToJsonString(customerMapper);
			
			Customer customer = iCustomerDao.saveCustomer(customerMapper);

			cifNoList.add(customer.getCustomerIdentificationNo());
		}
		return cifNoList;
	}
	
	@Override
	public List<String> createBulkNewCustomer(List<CustomerRequestDto> customerReqDto) {
		
		customerReqDto.stream().forEach(records->{
			String cifNo = createNewCustomer(records);
		});
		return null;
	}

	@Override
	public BulkCustomerSaveResponse importAndCreateNewCustomer(MultipartFile custDetailsFile) {
		List<String> errorList = new ArrayList<String>();
		List<String> cifNo = null;
		BulkCustomerSaveResponse seveResponse= new BulkCustomerSaveResponse();

		//Normalize the path by suppressing sequence like "path/..." and inner simple dots.
		String fileName= StringUtils.cleanPath(custDetailsFile.getOriginalFilename());
		logger.info("Start importing customer file name :: "+fileName);

		List<CustomerRequestDto> customerReqDto = importCustDetailFromExcel(custDetailsFile,errorList);
     /**
		Map<Boolean, List<CustomerRequestDto>> partitioned = customerReqDto.stream()
				.collect(Collectors.partitioningBy(req ->
				iCustomerDao.findCustomerByAadharAndPanNo(req.getAadharNumber(), req.getPanNumber()) != null
						));

		List<CustomerRequestDto> matched = partitioned.get(true);
		List<CustomerRequestDto> nonMatchedCustomerReq = partitioned.get(false);

		// Then extract CIF numbers for matched
		List<String> existCustomer = matched.stream()
				.map(req -> iCustomerDao.findCustomerByAadharAndPanNo(req.getAadharNumber(), req.getPanNumber()))
				.filter(Objects::nonNull)
				.map(Customer::getCustomerIdentificationNo)
				.collect(Collectors.toList());

		
		
		if(!existCustomer.isEmpty())
			seveResponse.setExistCustomer(existCustomer);

		//		if (isCustomerExist !=null) {
		//		logger.error("Customer already exist for this Pan :"+req.getPanNumber()+" & Aadhar No :"+req.getAadharNumber()+ "Find this CIF No:"+ +"!!!");
		//		}
		**/
		if(!customerReqDto.isEmpty())
			cifNo = mapCustDetailsAndSave(customerReqDto);

		seveResponse.setCifNo(cifNo);
		return seveResponse;
	}

	private List<CustomerRequestDto> importCustDetailFromExcel(MultipartFile file, List<String> errorList){
		
		List<CustomerRequestDto> custReqDtoList = new ArrayList<CustomerRequestDto>();
		List<CustomerAddressDto> addressDtoList = null;
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
				addressDtoList = new ArrayList<CustomerAddressDto>();
				customerDetailsDto = new CustomerDetailsDto();
				BranchDetailsRecord branch =null;
				StateDto state = null;
				CityDto city = null;
				Map<String,Object> cityAndStateMap = null;
				StringBuilder inputError = new StringBuilder();
				inputError.append("Row No : " + row.getRowNum() + " | " );
				if(row.getCell(0) == null) {
					branch= getBranchDetailsByBranchCode("");
					customerDetailsDto.setBranchId(branch.branchId());
				}else {
					branch= getBranchDetailsByBranchCode(CustomerUtil.getCellValue(row.getCell(0)));
					customerDetailsDto.setBranchId(branch.branchId());
				}
				
				custReqDto.setCustTitle(CustomerUtil.getCellValue(row.getCell(1)));
				
				if(row.getCell(2)== null) {
					logger.error("Name Cann't be blank ");
					inputError.append("Customer Name Blank | ");
				}else {	
					custReqDto.setFirstName(CustomerUtil.getCellValue(row.getCell(2)));
				}
				
				custReqDto.setMiddleName(CustomerUtil.getCellValue(row.getCell(3)));
				custReqDto.setLastName(CustomerUtil.getCellValue(row.getCell(4)));
				
				if(row.getCell(5) == null) {
					logger.error("Gender Cann't be blank");
					inputError.append("Customer Gender Blank | ");
				}else {	
					custReqDto.setGender(CustomerUtil.getCellValue(row.getCell(5)));
				}
				
				if(row.getCell(6) == null) {
					logger.error("Marital Status Cann't be blank");
					inputError.append("Customer Marital Status Blank | ");
				}else {	
					customerDetailsDto.setMaritalStatus(CustomerUtil.getCellValue(row.getCell(6)));
				}
				
				if(row.getCell(7) == null) {
					logger.error("Relation Type Cann't be blank");
					inputError.append("Customer Relation Type Blank | ");
				}else {	
					customerDetailsDto.setRelationType(CustomerUtil.getCellValue(row.getCell(7)));
				}
				
				if(row.getCell(8)== null) {
					logger.error("Husband/Father Title Cann't be blank");
					inputError.append("Customer Husband/Father Title Blank | ");
				}else {	
					customerDetailsDto.setHusbandFatherTitle(CustomerUtil.getCellValue(row.getCell(8)));
				}
				
				if(row.getCell(9) == null) {
					logger.error("Husband/Father Name Cann't be blank");
					inputError.append("Customer Husband/Father Name Blank | ");
				}else {	
					customerDetailsDto.setHusbandFatherName(CustomerUtil.getCellValue(row.getCell(9)));
				}
				
				if(row.getCell(10) != null) {                                
					customerDetailsDto.setMotherTitle(bankConfigClient.getMasGenderTitle("MRS").getTitleName());
					customerDetailsDto.setMotherRelation(bankConfigClient.getMasRelation("M").getRelationName()); 
					customerDetailsDto.setMotherName(CustomerUtil.getCellValue(row.getCell(10)));                                 
				}
				
				
				if(row.getCell(11) == null) {
					logger.error("DOB Cann't be blank");
					inputError.append("Customer DOB Blank | ");
				}else {	
					custReqDto.setDob(CustomerUtil.stringToLocalDate(CustomerUtil.getCellValue(row.getCell(11))));
					customerDetailsDto.setMajorDate(CustomerUtil.stringToLocalDate(CustomerUtil.getCellValue(row.getCell(11)))); 
				}
				
				
				if(row.getCell(12) == null) {
					logger.error("Aadhar Number Cann't be blank");
					inputError.append("Customer Aadhar Number Blank | ");
				}else if(row.getCell(13) == null) {
					logger.error("Pan Number Cann't be blank");
					inputError.append("Customer Pan Numbere Blank | ");
				}else {	
					Customer isExistCust = iCustomerDao.findCustomerByAadharAndPanNo(CustomerUtil.getCellValue(row.getCell(12)), CustomerUtil.getCellValue(row.getCell(13)));
					if(isExistCust != null) {
						logger.error("Customer already exist for this Pan :"+isExistCust.getPanNumber()+" & Aadhar No :"+isExistCust.getAadharNumber()+ "Find this CIF No:"+ isExistCust.getCustomerIdentificationNo()+"!!!");
						inputError.append("Customer already exist for this Pan :"+isExistCust.getPanNumber()+" & Aadhar No :"+isExistCust.getAadharNumber()+ "Find this CIF No:"+ isExistCust.getCustomerIdentificationNo()+"!!!");

					}else {
						custReqDto.setAadharNumber(CustomerUtil.getCellValue(row.getCell(12)));
						custReqDto.setPanNumber(CustomerUtil.getCellValue(row.getCell(13)));
					}
				}
				
				customerDetailsDto.setPassportNumber(row.getCell(14) == null ? null :CustomerUtil.getCellValue(row.getCell(14)));
				customerDetailsDto.setPassportPlaceIssue(row.getCell(15) == null ? null :CustomerUtil.getCellValue(row.getCell(15)));
				customerDetailsDto.setPassportIssueDate(row.getCell(16) == null ? null : CustomerUtil.stringToLocalDate(CustomerUtil.getCellValue(row.getCell(16))));
				customerDetailsDto.setPassportExpiryDate(row.getCell(17) == null ? null : CustomerUtil.stringToLocalDate(CustomerUtil.getCellValue(row.getCell(17))));
				
				
				if(row.getCell(18) == null) {
					logger.error("Contact No/ Mobile No Cann't be blank");
					inputError.append("Customer Contact No/ Mobil Name Blank | ");
				}else {
					custReqDto.setContactNo(CustomerUtil.getCellValue(row.getCell(18)));
				}
				
				custReqDto.setAlternateContactNo(row.getCell(19) == null ? null : CustomerUtil.getCellValue(row.getCell(19)));
				
				custReqDto.setEmailId(row.getCell(20) == null ? null : CustomerUtil.getCellValue(row.getCell(20)));
				
				customerDetailsDto.setIsMinor(CustomerUtil.getCellValue(row.getCell(21)) == "" ? 'N' : CustomerUtil.stringToCharcter(CustomerUtil.getCellValue(row.getCell(21))));
				customerDetailsDto.setHasNominee(CustomerUtil.getCellValue(row.getCell(22)) == "" ? 'N' : CustomerUtil.stringToCharcter(CustomerUtil.getCellValue(row.getCell(22))));
				customerDetailsDto.setHasGuardian(CustomerUtil.getCellValue(row.getCell(23)) == "" ? 'N' : CustomerUtil.stringToCharcter(CustomerUtil.getCellValue(row.getCell(23))));
				customerDetailsDto.setGuardianType(row.getCell(24) == null ? null : CustomerUtil.getCellValue(row.getCell(24)));
				customerDetailsDto.setNumDependents(row.getCell(25) == null ? 0 : Integer.valueOf(CustomerUtil.getCellValue(row.getCell(25))));
				
				// category code
				customerDetailsDto.setCategoryCode(row.getCell(26) == null ? null : CustomerUtil.getCellValue(row.getCell(26)));
				
				if(row.getCell(27) != null) {
					String custConstitution = CustomerUtil.getCellValue(row.getCell(27));
					CustomerConstitutionDto contitutionDto = bankConfigClient.getMasCustomerConst(custConstitution);
					customerDetailsDto.setConstitutionId(contitutionDto.getConId());//27
				}
				if(row.getCell(28) != null) {
					String religion = CustomerUtil.getCellValue(row.getCell(28));
					ReligionDto  religionDto = bankConfigClient.getMasReligion(religion);
					customerDetailsDto.setReligionId(religionDto.getReligionId());//28
				}
				if(row.getCell(29) != null) {
					String castCode = CustomerUtil.getCellValue(row.getCell(29));
					CastDto castDto = bankConfigClient.getMasCast(castCode);
					customerDetailsDto.setCasteId(castDto.getCastId());//29
				}
			
				if(row.getCell(30) != null) {
					String ocpCode = CustomerUtil.getCellValue(row.getCell(30));
					OccupationDto occupationDto = bankConfigClient.getMasOccupation(ocpCode);
					customerDetailsDto.setOccupationId(occupationDto.getOccupationId());//30
				}
				
//				if(row.getCell(31) != null) {
//					String educationCode = CustomerUtil.getCellValue(row.getCell(31));
//					CustomerConstitutionDto contitutionDto = bankConfigClient.getMasCustomerConst("INDV");
//					customerDetailsDto.setEducationQual(null);//31
//				}
				customerDetailsDto.setEducationQual(row.getCell(31) == null ? null : CustomerUtil.getCellValue(row.getCell(31)));//31
				
				if(row.getCell(32) != null) {
					String currencyCode = CustomerUtil.getCellValue(row.getCell(32));
					MasCurrencyDto currency = bankConfigClient.getMasCurrency(currencyCode,null);
					customerDetailsDto.setCurrencyId(currency.getCurrencyId());   //32
				}else {
					MasCurrencyDto currency = bankConfigClient.getMasCurrency(null,Long.valueOf(branch.countryId()));
					customerDetailsDto.setCurrencyId(currency.getCurrencyId());   //32
				}
				
				
				// Introduction type //33
				customerDetailsDto.setIntroductionType(row.getCell(33) == null ? null : CustomerUtil.getCellValue(row.getCell(33)));

				customerDetailsDto.setChecksumValue(null);                              
				customerDetailsDto.setNationalIdNumber(null);                           
				
			                                  
                customerDetailsDto.setMembershipNumber(null);                           
				customerDetailsDto.setEmployeeNumber(null);                             
				customerDetailsDto.setAccountManager(null);                             
				customerDetailsDto.setCustomerGroup(null);                              
				
				                      
				if(row.getCell(34) !=null && CustomerUtil.getCellValue(row.getCell(34)).equals("PERMANENT")) {
					addressDto = new CustomerAddressDto();
					addressDto.setAddressType(AddressType.valueOf(CustomerUtil.getCellValue(row.getCell(34))));
					addressDto.setHouseNumber(CustomerUtil.getCellValue(row.getCell(35)));
					addressDto.setAddressLine1(CustomerUtil.getCellValue(row.getCell(36)));
					addressDto.setAddressLine2(CustomerUtil.getCellValue(row.getCell(37)));
					addressDto.setAddressLine3(CustomerUtil.getCellValue(row.getCell(38)));
					addressDto.setStreet(CustomerUtil.getCellValue(row.getCell(39)));
					
					cityAndStateMap = findStateAndCity(branch.countryId() ,CustomerUtil.getCellValue(row.getCell(40)),CustomerUtil.getCellValue(row.getCell(41)));
					
					state = CustomerUtil.getMapObjectValue(cityAndStateMap, "STATE", StateDto.class);
					city = CustomerUtil.getMapObjectValue(cityAndStateMap, "CITY", CityDto.class);
					
					addressDto.setCityId(city.getCityId().intValue());//40
					addressDto.setStateId(state.getStateId().intValue());//41
					addressDto.setZipcode(CustomerUtil.getCellValue(row.getCell(42)));//42
					addressDtoList.add(addressDto);
				}
				if(row.getCell(43) !=null && (CustomerUtil.getCellValue(row.getCell(43)).equals("LOCAL")
						|| CustomerUtil.getCellValue(row.getCell(43)).equals("BUSINESS"))) {
					addressDto = new CustomerAddressDto();
					addressDto.setAddressType(AddressType.valueOf(CustomerUtil.getCellValue(row.getCell(43))));
					addressDto.setHouseNumber(CustomerUtil.getCellValue(row.getCell(44)));
					addressDto.setAddressLine1(CustomerUtil.getCellValue(row.getCell(45)));
					addressDto.setAddressLine2(CustomerUtil.getCellValue(row.getCell(46)));
					addressDto.setAddressLine3(CustomerUtil.getCellValue(row.getCell(47)));
					addressDto.setStreet(CustomerUtil.getCellValue(row.getCell(48)));
					
					cityAndStateMap = findStateAndCity(branch.countryId() ,CustomerUtil.getCellValue(row.getCell(49)),CustomerUtil.getCellValue(row.getCell(50)));
					
					state = CustomerUtil.getMapObjectValue(cityAndStateMap, "STATE", StateDto.class);
					city = CustomerUtil.getMapObjectValue(cityAndStateMap, "CITY", CityDto.class);
					
					addressDto.setCityId(city.getCityId().intValue());//49
					addressDto.setStateId(state.getStateId().intValue());//50
					addressDto.setZipcode(CustomerUtil.getCellValue(row.getCell(51)));//51
					addressDtoList.add(addressDto);
				}
				
				errorList.add(inputError.toString());
				
				
				custReqDto.setCustomerDetails(customerDetailsDto);
				custReqDto.setAddress(addressDtoList);
				
				custReqDtoList.add(custReqDto);
				
			//	logger.info("Customer Request Object is --> " +CustomerUtil.converJavaObjectToJsonString(custReqDto));
				
			}
		} catch (IOException e) {
			
			logger.error("Getting Error On importing Customer Details on this Row :: " + 1 +" Exception is :: "+e.getMessage());	}
		return custReqDtoList;
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
//				.createdBy(custReqDto.getCreatedBy())
//				.createdDate(customer.getCreatedDate())
//				.lastChgBy(custReqDto.getLastChgBy())
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
		bankConfigApi.getCityNameAndStateNameDetails(stateName, cityName);
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

	public BranchDetailsRecord getBranchDetailsByBranchCode(String branchCode) {
		return bankConfigApi.getBranchDetails(null, branchCode);
	}
	
	public BranchDetailsRecord getBranchDetailsByBranchId(Long branchId) {
		return bankConfigApi.getBranchDetails(branchId, null);
	}
		
	private List<StateDto> getAllState(long countryId) {
		return bankConfigApi.getAllState(countryId);
	}
	
	private List<CityDto> getAllCity(long stateId) {
		return bankConfigApi.getAllCity(stateId);
	}

	private Map<String,Object> findStateAndCity(long countryId,String stateName, String cityName){
		Map<String,Object> map = new HashMap<String, Object>();

		StateDto state = getAllState(countryId).stream()
				.filter(st -> st.getStateName() != null && st.getStateName().toLowerCase().equalsIgnoreCase(stateName))
				.findFirst()
				.orElse(null);

		CityDto city =   (state != null)
				? getAllCity(state.getStateId())
					.stream()
					.filter(ci -> ci.getCityName() != null && ci.getCityName().toLowerCase().equalsIgnoreCase(cityName))
					.findFirst()
					.orElse(null)
				: null;;

		map.put("STATE", state);
		map.put("CITY", city);
		return map;

	}
	
}
