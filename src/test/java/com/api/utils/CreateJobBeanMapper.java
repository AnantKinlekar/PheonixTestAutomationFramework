package com.api.utils;

import com.api.request.model.*;
import com.dataproviders.api.bean.CreateJobBean;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CreateJobBeanMapper {
    private static final Logger LOGGER = LogManager.getLogger(CreateJobBeanMapper.class);


    private CreateJobBeanMapper() {

    }

    @Step("Converting the CreateJobBean to the CreateJobPayload for CreateJobAPI Test")
    public static CreateJobPayload mapper(CreateJobBean bean) {
        LOGGER.info("Converting the CreateJobBean: {} to CreateJobPayload", bean);
        int mst_service_location_id = Integer.parseInt(bean.getMst_service_location_id());
        int mst_platform_id = Integer.parseInt(bean.getMst_platform_id());
        int mst_warrenty_status_id = Integer.parseInt(bean.getMst_warrenty_status_id());
        int mst_oem_id = Integer.parseInt(bean.getMst_oem_id());

        Customer customer = new Customer(bean.getCustomer__first_name(),
                bean.getCustomer__last_name(),
                bean.getCustomer__mobile_number(),
                bean.getCustomer__mobile_number_alt(),
                bean.getCustomer__email_id(),
                bean.getCustomer__email_id_alt());

        CustomerAddress customer_address = new CustomerAddress(bean.getCustomer_address__flat_number(),
                bean.getCustomer_address__apartment_name(),
                bean.getCustomer_address__street_name(),
                bean.getCustomer_address__landmark(),
                bean.getCustomer_address__area(),
                bean.getCustomer_address__pincode(),
                bean.getCustomer_address__country(),
                bean.getCustomer_address__country());

        int productId = Integer.parseInt(bean.getCustomer_product__product_id());
        int mst_model_id = Integer.parseInt(bean.getMst_oem_id());

        CustomerProduct customer_product = new CustomerProduct(bean.getCustomer_product__dop(),
                bean.getCustomer_product__serial_number(),
                bean.getCustomer_product__imei1(),
                bean.getCustomer_product__imei2(),
                bean.getCustomer_product__popurl(),
                productId,
                mst_model_id);

        List<Problems> problemList = new ArrayList<>();
        int problemId = Integer.parseInt(bean.getProblems__id());

        Problems problem = new Problems(problemId, bean.getProblems__remark());
        problemList.add(problem);

        CreateJobPayload createJobPayload = new CreateJobPayload(mst_service_location_id, mst_platform_id, mst_warrenty_status_id, mst_oem_id,
                customer,
                customer_address,
                customer_product,
                problemList);
        LOGGER.info("Converted the CreateJobBean to CreateJobPayload: {}", createJobPayload);

        return createJobPayload;
    }
}
