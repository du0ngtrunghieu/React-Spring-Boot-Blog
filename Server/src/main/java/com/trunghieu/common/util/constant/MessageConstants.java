package com.trunghieu.common.util.constant;

/**
 * Created on 9/4/2020.
 * Class: MessageConstants.java
 * By : Admin
 */
public class MessageConstants {
    public static final String EMAIL_USERNAME_ALREADY_EXISTS = "Email address or Username already exists !";
    public static final String ADMIN_INITIALIZATION = "Admin initialization !";
    public static final String ROLE_ALREADY_EXISTS = "Use role already exists !";
    public static final String USER_REGISTER_SUCCESS = "User registered successfully !";
    public static final String USER_REGISTER_FAIL = "User registered failure !";
    public static final String EMAIL_NOT_FOUND = "User not found with email ";
    public static final String NOT_SET_USER = "Could not set user authentication in security context !";
    public static final String INVALID_EMAIL_PASSW = "Invalid email address or password !";
    public static final String INVALID_EMAIL = "Invalid email address !";
    public static final String INVALID_PASSWORD = "Invalid password !";
    public static final String LOCKED = "User locked !";
    public static final String DISABLE = "User disable !";
    public static final String UNKNOWN = "User unknown !";
    public static final String ROLE_NOT_FOUND = "User not found with role !";
    public static final String CHANGE_PASSWORD_SUCCESS = "Change user password successfully !";
    public static final String INCORRECT_FILE = "Incorrect file format !";
    public static final String EMAIL_INVALIDATE = "Email invalidate !";
    public static final String EMAIL_NULL = "Email cannot be null !";
    public static final String PASSW_LENGTH = "Password length must be greater than 6 characters !";
    public static final String PASSW_NULL = "Password cannot be null !";
    public static final String DOB_NOT_CORRECT = "Birth date is not in the correct format dd/MM/yyyy !";
    public static final String PHONENUMBER_LENGTH = "Phone number cannot be less than 10 characters !";
    public static final String PHONE_NULL = "Phone number ccannot be null !";
    public static final String SEND_MAIL_SUCCESS = "Send mail Success !";
    public static final String RESET_PASSWORD_FAIL = "Reset password fail !";
    public static final String INVALID_FULLNAME= "Full name invalidate !";
    public static final String FULLNAME_NULL= "Full name cannot be null !";
    public static final String INVALID_PHONENUMBER= "Phone number invalidate!";
    public static final String UPDATE_EMPLOYEE_SUCCES = "Update employee succes";

    /* JWT */
    public static final String INVALID_JWT_SIGNATURE = "Invalid JWT signature";
    public static final String INVALID_JWT_TOKEN = "Invalid JWT token";
    public static final String EXPIRED_JWT_TOKEN = "Expired JWT token";
    public static final String UNSUPORT_JWT_TOKEN = "Unsupported JWT token";
    public static final String JWT_CLAIMS_EMPTY = "JWT claims string is empty.";

    public static final String SUCCESS = "Success";
    public static final String NOT_AUTHENTICATED = "User Not Authenticated";
    public static final String ACCESS_DENIED = "Access Is Denied";
    public static final String NOT_FOUND = "Not Found";
    public static final String BAD_REQUEST = "Bad Request";
    public static final String SERVER_ERROR = "Internal server error";

    public static final String LOG_LEVEL_WARN = "Log level warn !";

    public static final String FROZE_ROLE_ADMIN = "Can not set Permission for ROLE_ADMIN";
    public static final String INVALID_CONDITION = "Invalid orderBy condition";
    public static final String ROLE_PM_NOT_SET_ROLE_QCQA = "ROLE_PM can't set role for user is ROLE_QCQA";

    /*API Project*/
    public static final String PROJECT_NAME_EXIST = "This Project name was exist";
    public static final String PROJECT_NAME_IS_INVALID = "This Project name is invalid";
    public static final String PROJECT_CANNOT_DELETE = "This Project can not delete";
    public static final String PROJECT_START_DATE_IS_INVALID = "This Project startDate is invalid";
    public static final String PROJECT_END_DATE_IS_INVALID = "This Project endDate is invalid";
    public static final String PM_NOT_FOUND = "Not found Project Manager";
    public static final String LEADER_NOT_FOUND = "Not found Leader with this id";
    public static final String SIZE_OF_LIST_MEMBER_IS_INVALID = "Size of list member is invalid";
    public static final String MEMBER_LEFT_THE_PROJECT_SUCCESSFULLY = "Member left the project successfully";
    public static final String MEMBER_NOT_FOUND_IN_THIS_PROJECT = "Member not found in this project";
    public static final String MEMBER_HAS_BEEN_ADDED_TO_PROJECT = "This Member has been add to project";
    public static final String CANNOT_ADD_MEMBER_TO_PROJECT = "Can not add member into project, number of member is full";

    /*API Report*/
    public static final String MEMBER_NOT_EVALUATION_MEMBER = "Member can not evaluation other member";
    public static final String PM_NOT_EVALUATION_LEADER = "PM can not evaluation Leader";
    public static final String SUCCESS_EVALUATION = "Evaluation success !";
    public static final String FAIL_EVALUATION = "Evaluation fail !";
    public static final String VALIDATION_SCORES_EVALUATION = "score cannot be greater than 10 and be less than 0  !";
    public static final String REPORTS_INIT_SUCCESS = "Reports init success";
    public static final String REPORTS_CAN_NOT_INIT = "Reports can not init";


    /* Categories */
    public static final String CATEGORY_REGISTER_SUCCESS = "Thể loại tạo thành công !";
    public static final String CATEGORY_UPDATE_SUCCESS = "Thể loại update thành công !";
    public static final String CATEGORY_DELETE_SUCCESS = "Thể loại xoá thành công !";
    public static final String POST_REGISTER_SUCCESS = "Thêm bài viết thành công !";

    /* Tag */
    public static final String TAG_REGISTER_SUCCESS = "Tag tạo thành công !";
    public static final String TAG_DELETE_SUCCESS = "Tag xoá thành công !";
    public static final String TAG_UPDATE_SUCCESS = "Tag update thành công !";

    /* Permission */
    public static final String PERMISSION_REGISTER_SUCCESS = "Tạo Permission thành công !";
    public static final String PERMISSION_UPDATE_SUCCESS = "Permission update thành công !";
    public static final String PERMISSION_DELETE_SUCCESS = "Permission delete thành công !";

    /* ROLE HAS PERMISSION */
    public static final String ROLE_PERMISSION_REGISTER_SUCCESS = "Tạo Role has Permission thành công !";
    public static final String ROLE_PERMISSION_DELETE_SUCCESS = "Role has Permission delete thành công !";

    /* User */
    public static final String USER_DELETE_SUCCESS ="Người dùng này đã xoá thành công !";
    public static final String USER_UPDATE_SUCCESS ="Update thông tin user thành công !";
}
