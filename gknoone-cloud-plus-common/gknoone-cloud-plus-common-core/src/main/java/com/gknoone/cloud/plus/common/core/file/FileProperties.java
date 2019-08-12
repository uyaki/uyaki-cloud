package com.gknoone.cloud.plus.common.core.file;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文件配置读取
 *
 * @author noone
 * @date 2019-06-27 18:17
 */
@ConfigurationProperties(prefix = "file")
@Getter
@Setter
public class FileProperties {
    /**
     * 上传路径
     */
    private String uploadUrl;
    /**
     * 下载读取路径
     */
    private String downloadUrl;
    /**
     * 临时缓存路径
     */
    private String tempUrl;
    /**
     * 是否保留zip包
     */
    private Boolean reservedZip;
}
