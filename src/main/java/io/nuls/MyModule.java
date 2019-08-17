package io.nuls;

import io.nuls.controller.core.WebServerManager;
import io.nuls.core.core.annotation.Autowired;
import io.nuls.core.core.annotation.Component;
import io.nuls.core.rpc.model.ModuleE;
import io.nuls.core.rpc.modulebootstrap.Module;
import io.nuls.core.rpc.modulebootstrap.RpcModuleState;
import io.nuls.rpc.TransactionTools;

import java.io.File;

/**
 * @Author: zhoulijun
 * @Time: 2019-06-10 20:54
 * @Description: 功能描述
 */
@Component
public class MyModule {

    @Autowired
    Config config;

    @Autowired
    TransactionTools transactionTools;

    public RpcModuleState startModule(String moduleName){
        //初始化数据存储文件夹
        File file = new File(config.getDataPath());
        if(!file.exists()){
            file.mkdir();
        }
        //注册交易
        transactionTools.registerTx(
                moduleName,
                Constant.TX_TYPE_ONCHAIN_REGISTER,
                Constant.TX_TYPE_ONCHAIN_DEPOSIT);
        //初始化web server
        WebServerManager.getInstance().startServer("0.0.0.0", 9999);
        return RpcModuleState.Running;
    }

    public Module[] declareDependent() {
        return new Module[]{
                Module.build(ModuleE.LG),
                Module.build(ModuleE.TX),
                Module.build(ModuleE.NW)
        };
    }

}
