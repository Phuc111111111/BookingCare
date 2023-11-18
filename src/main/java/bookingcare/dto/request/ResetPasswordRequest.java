package bookingcare.dto.request;

public class ResetPasswordRequest {
    private String newPassword;
    private String rePassword;

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String newPassword, String rePassword) {
        this.newPassword = newPassword;
        this.rePassword = rePassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}
