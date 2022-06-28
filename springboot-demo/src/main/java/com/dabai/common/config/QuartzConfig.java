package com.dabai.common.config;

/**
 * @author
 * @create 2022-06-22 10:01
 */
// 配置 -> 数据库 -> 调用
// 本系统暂不使用定时任务，部署后再用
//@Configuration
public class QuartzConfig {

    // FactoryBean 可简化Bean的实例化过程：
    // 1. 通过FactoryBean封装Bean的实例化过程
    // 2. 将FactoryBean装配到Spring容器里
    // 3. 将FactoryBean注入给其他的Bean
    // 4. 该Bean得到的是FactoryBean所管理的对象实例

    // 配置JobDetail
    // 此处为demo，之后不再使用，所以注释了@Bean
//    @Bean
//    public JobDetailFactoryBean testJobDetail() {
//        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
//        factoryBean.setJobClass(TestJob.class);
//        factoryBean.setName("testJob");
//        factoryBean.setGroup("testJobGroup");
//        factoryBean.setDurability(true);
//        factoryBean.setRequestsRecovery(true);
//        return factoryBean;
//    }

    // 配置Trigger（simpleTriggerFactoryBean, CronTriggerFactoryBean）
//    @Bean
//    public SimpleTriggerFactoryBean testJobTrigger(JobDetail testJobDetail) {
//        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
//        factoryBean.setJobDetail(testJobDetail);
//        factoryBean.setName("testJobTrigger");
//        factoryBean.setGroup("testJobTriggerGroup");
//        factoryBean.setRepeatInterval(3000);
//        factoryBean.setJobDataMap(new JobDataMap());
//        return factoryBean;
//    }

}
