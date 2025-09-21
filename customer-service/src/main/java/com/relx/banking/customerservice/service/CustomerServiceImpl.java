package com.relx.banking.customerservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.relx.banking.customerservice.client.AccountClient;
import com.relx.banking.customerservice.dao.ICustomerDao;
import com.relx.banking.customerservice.dto.CustomerAddressDto;
import com.relx.banking.customerservice.dto.CustomerDetailsDto;
import com.relx.banking.customerservice.dto.CustomerRequestDto;
import com.relx.banking.customerservice.dto.CustomerResponseDto;
import com.relx.banking.customerservice.dto.JointAccountDto;
import com.relx.banking.customerservice.entity.Address;
import com.relx.banking.customerservice.entity.Customer;
import com.relx.banking.customerservice.enums.GenderEnum;
import com.relx.banking.customerservice.mappers.AddressMapper;
import com.relx.banking.customerservice.mappers.CustomerMapper;
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
	
	@Override
	public String createNewCustomer(CustomerRequestDto customerRequest) {
		List<Address> addressList = new ArrayList<Address>();
		Customer isCustomerExist = iCustomerDao.findCustomerByAadharAndPanNo(customerRequest.getAadharNumber(), customerRequest.getPanNumber());
				if (isCustomerExist !=null) {
					throw new InvalidCustomerException("Customer already exist !!!");
				}

		Customer customerMapper = CustomerMapper.INSTANCE.customerDTOToCustomer(customerRequest);
		if(customerRequest.getAddress() !=null && !customerRequest.getAddress().isEmpty()) {
			for(CustomerAddressDto custAddressDto: customerRequest.getAddress()) {
				Address addressMapper = AddressMapper.INSTANCE.customerAddressDTOToCustomerAddress(custAddressDto);
				addressMapper.setCustomer(customerMapper);
				addressList.add(addressMapper);
			}
		}
		
		customerMapper.setAddress(addressList);
		customerMapper.setStatus("Active");
		Customer customer = iCustomerDao.saveCustomer(customerMapper);

		return customer.getCustomerIdentificationNo();
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
	
	
	public CustomerDetailsDto getCustomerWithAccounts(Long customerId) {
        Customer customer = iCustomerDao.getCustomerDetails(customerId,"Active");
       
        // Fetch accounts from account-service
        List<JointAccountDto> accounts = accountClient.getAccountsByCustomerId(customerId);

        return new CustomerDetailsDto(
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
	
//	@Cacheable("bankDate")
//    public LocalDate getBankDate(Long bankId) {
//        String url = configServiceUrl + "/api/config/bank-date/" + bankId;
//        return restTemplate.getForObject(url, LocalDate.class);
//    }

	
//	@Scheduled(fixedDelay = 60000) // every 1 min refresh
//    public void refreshBankDate() {
//        cacheManager.getCache("bankDate").clear();
//    }

}
