package recommender.service;


import recommender.domain.Template;

/**
 * Describe: 规则配置服务
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/12/2.
 */
public interface RuleService {
    Template getTemplateByAdId(String adId);

    boolean isExist(String adId);
}
