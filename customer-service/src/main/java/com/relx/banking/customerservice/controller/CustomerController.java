package com.relx.banking.customerservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.relx.banking.customerservice.dto.BulkCustomerSaveResponse;
import com.relx.banking.customerservice.dto.CustomerRequestDto;
import com.relx.banking.customerservice.service.ICustomerService;
import com.relx.banking.customerservice.util.exceptionhandling.ApiResponse;


/**
 * @author Naveen Sankhala
 */
@RestController
@RequestMapping("/customer")
//@CrossOrigin(origins = "${ui.cross.url}")
//@Tag(name ="Account-controller", description = "Set of end points retrieving & store Account details")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	//add initBinder ... to convert trim input string
	//remove leading and trailing whitespace
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@Autowired
	private ICustomerService iCustomerService;
	
	@Autowired
	private MessageSource messageSource;
	
	@PostMapping("")
	public ResponseEntity<?> createNewCustomer(@RequestBody CustomerRequestDto customerRequestDto) throws Exception{
		String cifNo = iCustomerService.createNewCustomer(customerRequestDto);
		if(cifNo !=null)
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true,String.format("Customer create successfuly , CIF ID :: "+cifNo)));
		else
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(false,messageSource.getMessage("4", null,LocaleContextHolder.getLocale())));
	}
	
	@PostMapping("bulk")
	public ResponseEntity<?> createBulkNewCustomer(@RequestBody List<CustomerRequestDto> customerReqDto) throws Exception{
		List<String> cifNo = iCustomerService.createBulkNewCustomer(customerReqDto);
		if(cifNo !=null)
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true,String.format("Customer create successfuly , CIF ID :: "+cifNo)));
		else
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(false,messageSource.getMessage("4", null,LocaleContextHolder.getLocale())));
	}
	
	@PostMapping(value ="import" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> importAndCreateNewCustomer(@RequestParam("cust-detail-file") MultipartFile custDetailsFile) throws Exception{
		BulkCustomerSaveResponse response = iCustomerService.importAndCreateNewCustomer(custDetailsFile);
		if(response !=null)
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true,String.format("Customer create successfuly , CIF ID :: ")));
		else
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(false,messageSource.getMessage("4", null,LocaleContextHolder.getLocale())));
	}

	@GetMapping("")
	public ResponseEntity<?> getCustomerDetails(
			@RequestParam(name ="cif-no" , required = false) String cifNo,
			@RequestParam(name ="customerId", required = false) Long customerId)throws Exception{
		
		if (cifNo != null) {
	        return ResponseEntity.status(HttpStatus.OK).body(iCustomerService.getCustomerDetails(cifNo));
	    } else if (customerId != null) {
	        return ResponseEntity.status(HttpStatus.OK).body(iCustomerService.getCustomerDetails(customerId));
	    }
	    return ResponseEntity.badRequest().body("Either cifNo or customerId must be provided");
		//return ResponseEntity.status(HttpStatus.OK).body(iCustomerService.getCustomerDetails(cifNo));
	}
	
//	@GetMapping("")
//	public ResponseEntity<?> getCustomerDetails(@RequestParam("customerId") Long customerId)throws Exception{
//		return ResponseEntity.status(HttpStatus.OK).body(iCustomerService.getCustomerDetails(customerId));
//	}
		
	@PutMapping("")
	public ResponseEntity<?> updateAccountMetaData(@RequestBody CustomerRequestDto customerRequestDto)throws Exception{
		boolean result =iCustomerService.updateCustomerMetaData(customerRequestDto);
		if(result)
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true,String.format("Customer Details Updated successfuly , CIF ID :: "+customerRequestDto.getCifNo())));
		else
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(false,messageSource.getMessage("4", null,LocaleContextHolder.getLocale())));
	
	}
	
	@PatchMapping("")
	public ResponseEntity<?> activeDeactiveAccount(@RequestParam("cif-no") String cifNo)throws Exception{
		boolean result = iCustomerService.activeDeactiveAccount(cifNo);
		if(result)
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true,String.format("Customer Activate successfuly , CIF ID :: "+cifNo)));
		else
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(false,messageSource.getMessage("4", null,LocaleContextHolder.getLocale())));
	}
	
	@GetMapping("/all-customer")
	public ResponseEntity<?> getAllCustomer(@RequestParam(required = false) String custName,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "100") int size)throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(iCustomerService.getAllCustomer(custName,page,size));
	}
	
	@GetMapping("/{cif-no}")
	public ResponseEntity<?> verifyCustomer(@PathVariable("cif-no") String cifNo)throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(iCustomerService.verifyCustomer(cifNo));
	}
}
