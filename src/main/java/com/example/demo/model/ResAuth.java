package com.example.demo.model;

public class ResAuth {
    private String accessToken;
    private String tokenType = "Bearer";

    public ResAuth(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
	@Override
	public String toString() {
		return "ResAuth [accessToken=" + accessToken + ", tokenType=" + tokenType + "]";
	}
    
}


