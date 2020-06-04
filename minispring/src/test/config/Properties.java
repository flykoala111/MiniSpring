package test.config;

import com.fzj.minispring.annotions.Anno_fzjConfiguraton;
import com.fzj.minispring.annotions.Anno_fzjFlexibility;
import com.fzj.minispring.annotions.Anno_fzjProperties;

/**
 * 获取配置文件的属性
 **/
@Anno_fzjConfiguraton
@Anno_fzjFlexibility
public class Properties {
    //项目名称
    @Anno_fzjProperties
    public String ProjectName;
    //版本号
    @Anno_fzjProperties
    public String ProjectVersion;
    //作者
    @Anno_fzjProperties
    public String Author;
}
