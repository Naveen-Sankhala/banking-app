package com.relx.banking.accountservice.enums;

/**
 * @author Naveen.Sankhala
 * Sep 9, 2025
 */
public enum AccountType {
	// ------------------ Deposit / Liability Accounts ------------------
	SAVINGS("Savings Account"),
	CURRENT("Current Account"),
	FIXED_DEPOSIT("Fixed Deposit"),
	RECURRING_DEPOSIT("Recurring Deposit"),
	SALARY_ACCOUNT("Salary Account"),
	NRI_NRE("NRI - NRE Account"),
	NRI_NRO("NRI - NRO Account"),
	NRI_FCNR("NRI - FCNR Account"),
	CERTIFICATE_OF_DEPOSIT("Certificate of Deposit"),

	// ------------------ Loan / Asset Accounts ------------------
	PERSONAL_LOAN("Personal Loan"),
	HOME_LOAN("Home Loan"),
	VEHICLE_LOAN("Vehicle Loan"),
	EDUCATION_LOAN("Education Loan"),
	BUSINESS_LOAN("Business Loan"),
	AGRICULTURAL_LOAN("Agricultural Loan"),
	GOLD_LOAN("Gold Loan"),
	CREDIT_CARD("Credit Card"),
	OVERDRAFT("Overdraft"),
	CASH_CREDIT("Cash Credit"),

	// ------------------ Special / Other Accounts ------------------
	DEMAT_ACCOUNT("Demat Account"),
	INVESTMENT_ACCOUNT("Investment Account"),
	JOINT_ACCOUNT("Joint Account"),
	MINOR_ACCOUNT("Minor Account"),
	ESCROW_ACCOUNT("Escrow Account"),
	TRUST_ACCOUNT("Trust Account"),
	PENSION_ACCOUNT("Pension Account"),
	NOSTRO_ACCOUNT("Nostro Account"),
	VOSTRO_ACCOUNT("Vostro Account"),
	SUSPENSE_ACCOUNT("Suspense / Sundry Account");

	private final String accountType;

	AccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountType() {
		return accountType;
	}

	// Optional: helper to get enum by name ignoring case
	public static AccountType fromString(String value) {
		for (AccountType type : AccountType.values()) {
			if (type.name().equalsIgnoreCase(value) || type.accountType.equalsIgnoreCase(value)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Unknown account type: " + value);
	}
}
