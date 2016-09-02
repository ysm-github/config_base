package cn.com.sxit.common.version;


/**  
 *     @author     yushiming
 *     @company    sxit	
 *     @version    1.0.0
 *     @since      1.0.0
 *     @date       2013-12-6
 *     @CheckItem
 */
public class VersionVO
{

    private String versionID;

    private String upTime;

    private String ownerInfo;

    public String getOwnerInfo()
    {

        return ownerInfo;
    }

    public void setOwnerInfo(String ownerInfo)
    {

        this.ownerInfo = ownerInfo;
    }

    public void setVersionID(String versionID)
    {

        this.versionID = versionID;
    }

    public void setUpTime(String upTime)
    {

        this.upTime = upTime;
    }

    public String getUpTime()
    {

        return upTime;
    }

    public String getVersionID()
    {

        return versionID;
    }
}

