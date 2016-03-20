/**
 * 
 * @author binita.bharati@gmail.com
 * A sample model class, used for converting json string to Java object.
 * See com.github.binitabharati.jilapi.SampleTest:testSample4() test case.
 *
 */
package com.github.binitabharati.jilapi.sample.model;

public class EtcPasswd {
    
    private String userName;
    private String passwd;
    private String userId;
    private String grpId;
    private String userFullName;
    private String homeDirectory;
    private String shellAccount;
    
    public EtcPasswd(String userName, String passwd, String userId, String grpId, String userFullName,
            String homeDirectory, String shellAccount) {
        super();
        this.userName = userName;
        this.passwd = passwd;
        this.userId = userId;
        this.grpId = grpId;
        this.userFullName = userFullName;
        this.homeDirectory = homeDirectory;
        this.shellAccount = shellAccount;
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof EtcPasswd) {
            EtcPasswd temp = (EtcPasswd)obj;
            if (temp.grpId.equals(this.grpId) && temp.homeDirectory.equals(this.homeDirectory)
                    && temp.passwd.equals(this.passwd) && temp.shellAccount.equals(this.shellAccount)
                    && temp.userFullName.endsWith(this.userFullName) && temp.userId.equals(this.userId)
                    && temp.userName.equals(this.userName)) {
                return true;
            }
            
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return homeDirectory.hashCode();
    }

}
