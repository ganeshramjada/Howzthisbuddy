package com.pivotaldesign.howzthisbuddy.bean;

public class UserProfilePictureBO extends HowZThisBuddyBaseBO {
	private static final long serialVersionUID = 1L;
	private static final String className = UserProfilePictureBO.class
			.getName();

	private long phoneNumber;
	private byte[] profilePic;

	public UserProfilePictureBO() {

	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public byte[] getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}

	public String toString() {
		return "className : " + className + "{\n " + "\n }";
	}
}
