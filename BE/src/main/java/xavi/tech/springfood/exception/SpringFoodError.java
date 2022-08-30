package xavi.tech.springfood.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum SpringFoodError {
	
	/*
	*
	* O - SPRINGFOOD 
	* 1 - ACCOUNTS / 11 - ROLES
	* 2 - PRODUCTS
	* 6 - CLOUDINARY
	* 8 - ORDERS / 89 - STRIPE
	*
	*/
	
	InternalError("000","Internal Springfood Error"),
	CfgInvalidTimeslot("010","Invalid time slot value"),
	CfgInvalidOpenTime("011","Invalid open time value"),
	CfgInvalidCloseTime("012","Invalid close time value"),
	InvalidUser("100","User not found or wrong credentials"),
	GeneralLoginError("101","General login error"),
	EmailAlreadyExists("102","This email is already registered"),
	ErrorCreatingClient("110","Error creating client"),
	ErrorCreatingWorker("120","Error creaing worker"),
	InvalidRole("130","Account role is not valid"),
	InvalidJWToken("140","Invalid token"),
	InvalidJWTokenFormat("141","Invalid token format"),
	ExpiredJWToken("142","The token has expired"),
	ErrorCreatingProduct("200","Error creating product"),
	EmptyProductList("201","Database has no products"),
	CloudinaryError("600","Error with Cloudinary API"),
	CloudinaryNullImg("601","Invalid image format"),
	CloudinaryMpfParse("602","Error parsing MPF to FILE"),
	CloudinaryUploadError("604","Error uploading to Cloudinary"),
	CloudinaryDeleteError("605","Error deleting from Cloudinary"),
	OrderPriceAlteration("808","Client side price modification detected"),
	StripeError("890","Error with Stripe API"),
	StripeCardBlocked("891","Payment blocked for suspected fraud"),
	StripeCardInsufficientFunds("892","Your card has insufficient funds"),
	StripeCardExpired("893","The card has expired. Check the expiration date or use a different card"),
	StripeCardCallIssuer("894","The card was declined for an unknown reason"),
	StripeCardIncorrectCVC("895","The card’s security code is incorrect. Check the card’s security code"),
	StripeCardIncorrectNumber("896","The card number is incorrect. Check the card’s number");




	private String code;
	private String description;
	
		
}
