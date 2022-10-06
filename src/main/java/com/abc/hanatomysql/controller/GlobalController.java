package com.abc.hanatomysql.controller;


import com.abc.hanatomysql.common.Result;
import com.abc.hanatomysql.model.SyncDTO;
import com.abc.hanatomysql.service.IGlobalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  统一同步接口
 * </p>
 *
 * @author Z-7
 * @since 2022-08-18
 */
@Api(tags = "统一同步接口")
@RestController
@RequestMapping("/hana")
public class GlobalController {
    @Autowired
    private IGlobalService iGlobalService;

    @PostMapping("/sync")
    @ApiOperation("同步接口")
    @ApiImplicitParam(name = "执行对象", value = "sycnDTO", required = true)
    public Result hanaToMysql(SyncDTO sycnDTO) {
        iGlobalService.getConnetion();
        Result result = iGlobalService.hanaToMysql(sycnDTO);
        iGlobalService.close();

        return result;
    }
}
