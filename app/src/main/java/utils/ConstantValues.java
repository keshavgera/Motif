package utils;

import android.content.Context;

public class ConstantValues {

	public static final String DISPLAY_MESSAGE_ACTION =
			"com.codecube.saurabh.myKumpany";

	public static final String EXTRA_MESSAGE = "message";
	Context context;
	public static final String COMMONURL = "http://52.11.140.186/Brindco/webservices/api/";
	public static final String MYKUMPANY = "http://52.11.140.186/myKumpanyDevNT/webServices/api/";




	public static final String Motif = "http://52.11.140.186/motif/webService/api/";

	public static final String LOGIN = Motif + "login";

	public static final String CHANGEPASSWORD = Motif + "signUp";
	public static final String UploadRequirements = Motif + "uploadRequirements";
	public static final String GetCorporateList = Motif + "uploadRequirements";
	public static final String PostInventory = Motif + "postInventory";						// post in broker
	public static final String GetPostInventory = Motif + "postInventory";			// show in corporate
	public static final String UserNotificationSetting = Motif + "userNotificationSetting";			// show in corporate
	public static final String Message = Motif + "message";			// show in corporate
	public static final String GetMessage = Motif + "message";			// show in corporate
	public static final String News = Motif + "news";			// show in corporate
	public static final String LocalityFetch = Motif + "locality_fetch";
	public static final String City = Motif + "city";
































	public static final String SALESCAPTURE = MYKUMPANY + "addSalesCapture";
	public static final String STOCKIN = MYKUMPANY + "addStockIn";
	public static final String COMPETITIONSALES = MYKUMPANY + "addSalesCompetitor";
	public static final String INVENTORY = MYKUMPANY + "addSalesInventory";
	public static final String GETMODELLIST = MYKUMPANY + "getModelList";
	public static final String GETSALESGRAPH = MYKUMPANY + "getSalesGraph";
	public static final String GETATTENDANCEGRAPH = MYKUMPANY + "getAttandenceGraph";
	public static final String GETFEEDBACKTYPES = MYKUMPANY + "getFeedbackTypes";
	public static final String ADDFEEDBACK = MYKUMPANY + "addFeedback";
	public static final String GETCITYLIST = MYKUMPANY + "cityName";
	public static final String GETOFFICETYPE = MYKUMPANY + "getOfficeType";
	public static final String SALESCOMPARISON = MYKUMPANY + "getSaleComparison";
	public static final String GETSALES = MYKUMPANY + "getSales";
	public static final String ADDDAILYACTIVITY = MYKUMPANY + "addDailyActivity";
	public static final String SAVEPHONEDETAILS = MYKUMPANY + "addAppVersion";
	//public static final String TEAMATTENDANCE = MYKUMPANY + "getContactList";
	public static final String CHECKIN = MYKUMPANY + "userCheckin";
	public static final String IMAGEUPLOAD = MYKUMPANY + "imageUploads";
	public static final String TYPEOFVISIT = MYKUMPANY + "typeOfVisit";
	public static final String CITYBYSTATE = MYKUMPANY + "cityByState";
	public static final String GETCOMPANYINFO = MYKUMPANY + "companyInfo";
	public static final String AddAttendance = MYKUMPANY + "addAttendance";
	public static final String GetDailyActivity = MYKUMPANY + "getDailyActivity";
	public static final String ADDCUSTOMER = MYKUMPANY + "customerAdd";
	public static final String CREATEUSERNOTE = MYKUMPANY + "CreateUserNote";
	public static final String ADDSALES = MYKUMPANY + "addSales";
	public static final String FORGOTPASSWORD = MYKUMPANY + "forgotPassword";
	public static final String FORCEUPDATE = MYKUMPANY + "getForceUpdate";
	public static final String GETNOTELIST = MYKUMPANY + "noteList";
	public static final String CUSTOMERLIST = MYKUMPANY + "customerLists";
	public static final String GETSTOCK = MYKUMPANY + "getStocks";
	public static final String GETSTOCKREPORT = MYKUMPANY + "getStockReport";
	public static final String GETFEEDBACK = MYKUMPANY + "getFeedbackList";
	public static final String FEEDBACKCLOSED = MYKUMPANY + "feedbackStatus";
	public static final String GETCATEGORY = MYKUMPANY + "getProductCategory";
	public static final String GETCATALOGUE = MYKUMPANY + "getProductCatelog";
	public static final String GETNEWS = MYKUMPANY + "getAllnews";
	public static final String CHECKIMEI = MYKUMPANY + "getImeiDetails";
	public static final String IMEIDETAILS = MYKUMPANY + "checkMyImeiNoInStock";
	public static final String GetTeam = MYKUMPANY + "getTeam";
	public static final String AttendanceByTeam = MYKUMPANY + "attendenceByTeam";
	public static final String PjpByTeam = MYKUMPANY + "pjpByTeam";
	public static final String SalesByTeam = MYKUMPANY + "salesByTeam";
	public static final String CollectionByTeam = MYKUMPANY + "collectionByTeam";
	public static final String MARKEDNOTMARKEDATTENDANCE = MYKUMPANY + "getTeamAttendence";
	public static final String AttendanceReport = MYKUMPANY + "attendanceReport";
	public static final String GETLEAVETYPES=MYKUMPANY + "getLeaveTypes";
	public static final String ADDLEAVE = MYKUMPANY + "addMyLeave";
	public static final String GETLEAVELIST = MYKUMPANY + "getMyLeaveList";
	public static final String LEAVERECEIVED = MYKUMPANY + "leaveReceived";
	public static final String ADDITEMWISESALE = MYKUMPANY + "addItemWiseSale";
	public static final String GETCATSUBCATITEM = MYKUMPANY + "getCatSubCatItem";
	public static final String GETMYITEMWISESALE = MYKUMPANY + "getMyItemWiseSale";
	public static final String GETSKUFROMITEMWISE = MYKUMPANY + "getSKUForItemWiseSale";
	public static final String FEEDBACKRECEIVED = MYKUMPANY + "feedbackReceived";
	public static final String LEAVECLOSED = MYKUMPANY + "leaveStatus";
	public static final String GETCUSTOMERLIST = MYKUMPANY + "getcustomerlist";
	public static final String ADDCUSTOMERLEAD = MYKUMPANY + "addCustomerLead";
	public static final String GETTYPES = MYKUMPANY + "getLeadStatus";
	public static final String GETPRODUCTTYPES = MYKUMPANY + "getProductList";
	public static final String GETLEADLIST = MYKUMPANY + "getCustomerLead";
	public static final String ADDCUSTOMERPLAN = MYKUMPANY + "addCustomerPlan";
	public static final String GETCUSTOMERPLANS = MYKUMPANY + "gettourPlan";
	public static final String GETHOMEWEBVIEW = MYKUMPANY + "getChart";
	public static final String Location =MYKUMPANY + "addPolling";
	public static final String GETINDIVIDUALCHART = MYKUMPANY + "getIndividualChart";
	public static final String GETOVERALLCHART = MYKUMPANY + "getOverAllChart";
	public static final String GETTEAMCHART = MYKUMPANY + "getTeamChart";
	public static final String GETORDERLIST2 = MYKUMPANY + "getOrder";
	public static final String GetCategorySubCategory = MYKUMPANY + "getCategorySubCategory";
	public static final String GETNOTIFICATIONS = MYKUMPANY + "getproc_pushnotification_vw";
	public static final String SALEORDER = MYKUMPANY + "getsaleOrder";
	public static final String GetCompetitorList = MYKUMPANY + "getCompetitorList";
	public static final String CUSTOMERlEADPROC = MYKUMPANY + "getleads";
	public static final String COLLECTIONLIST = MYKUMPANY + "getCollections";
	public static final String ADDCOLLECTION = MYKUMPANY + "addCollections";
	public static final String GETGEOLOCATION = MYKUMPANY + "getGeoLocation";
	public static final String IMAGEUPLOADProfile = MYKUMPANY + "addProfileImage";
	public static final String PHONEBOOKlIST = MYKUMPANY + "getPhoneList";
	public static final String GETMAP = MYKUMPANY + "getMap";
	public static final String UPDATESETTING = MYKUMPANY + "updateSetting";
	public static final String USERCHECKIN = MYKUMPANY + "userCheckin";
	public static final String GetEmployeeName = MYKUMPANY + "getEmployeeName";
	public static final String GetTypeMasterTypeName = MYKUMPANY + "getTypeMasterTypeName";
	public static final String Getproject = MYKUMPANY + "getproject";
	public static final String GetTask = MYKUMPANY + "getTask";
	public static String CHECKLIST_URL=MYKUMPANY + "getCheckList";
	public static String SAVECHECKLIST_URL=MYKUMPANY + "saveCheckList";
	public static String VIEWCHECKLIST_URL=MYKUMPANY + "viewCheckList";


	//-------------------------------------------
/*	public static final String NEWS = COMMONURL + "news";
	public static final String ORDER = COMMONURL + "order";
	public static final String GETCOLLECTIONLIST = COMMONURL + "getCollection";
	public static final String COLLECTION = COMMONURL + "addCollection";
	public static final String PRODUCTLIST = COMMONURL + "getProductList";*/





	public static final String imgLoad = "http://52.11.140.186/Brindco/uploads/";
	public static String uploadServerUri = COMMONURL + "uploads";


}