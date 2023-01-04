package com.example.demo.model;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@Data
public class OauthToken {

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class response{

        private String access_token;
        private String token_type;
        private String refresh_token;
        private long expires_in;
        private String scope;
		public String getAccess_token() {
			return access_token;
		}
		public String getToken_type() {
			return token_type;
		}
		public String getRefresh_token() {
			return refresh_token;
		}
		public long getExpires_in() {
			return expires_in;
		}
		public String getScope() {
			return scope;
		}
		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}
		public void setToken_type(String token_type) {
			this.token_type = token_type;
		}
		public void setRefresh_token(String refresh_token) {
			this.refresh_token = refresh_token;
		}
		public void setExpires_in(long expires_in) {
			this.expires_in = expires_in;
		}
		public void setScope(String scope) {
			this.scope = scope;
		}

    }

    @Data
    public static class request{

        @Data
        public static class accessToken{
            public String code;
            private String grant_type;
            //redirect 다음
            private String redirect_uri;

			public Map getMapData(){
                Map map = new HashMap();
                map.put("code",code);
                map.put("grant_type",grant_type);
                map.put("redirect_uri",redirect_uri);
                return map;
            }

			public String getCode() {
				return code;
			}

			public String getGrant_type() {
				return grant_type;
			}

			public String getRedirect_uri() {
				return redirect_uri;
			}

			public void setCode(String code) {
				this.code = code;
			}

			public void setGrant_type(String grant_type) {
				this.grant_type = grant_type;
			}

			public void setRedirect_uri(String redirect_uri) {
				this.redirect_uri = redirect_uri;
			}
        }

        @Data
        public static class refrashToken{
            private String refreshToken;
            private String grant_type;

            public Map getMapData(){
                Map map = new HashMap();
                map.put("refresh_token",refreshToken);
                map.put("grant_type",grant_type);
                return map;
            }
        }
    }
}
