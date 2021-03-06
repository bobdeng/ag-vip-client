package com.anguokeji.smartlock.vipclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.anguokeji.smartlock.vipclient.forms.*;
import okhttp3.*;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.signers.RSADigestSigner;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VipClientImpl implements VipClient {
    private VipConfig vipConfig;
    private OkHttpClient okHttpClient;

    public VipClientImpl(VipConfig vipConfig) {
        this.vipConfig = vipConfig;
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(100, TimeUnit.SECONDS)
                .build();
    }

    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json");
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";


    private <T> T callRequest(String url, String method, Object form, Type type) {

        Request request = buildRequest(url, method, form);
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                BaseResult<T> result = JSON.parseObject(response.body().string(), type);
                if (result == null) {
                    throw new RuntimeException("server error");
                }
                if (result.getCode() != 0) {
                    throw new RuntimeException(result.getError());
                }
                return result.getData();
            } else {
                throw new RuntimeException(response.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T callRequestWithoutBaseResult(String url, String method, Object form, Type type) {

        Request request = buildRequest(url, method, form);
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                T result = JSON.parseObject(response.body().string(), type);
                return result;
            } else {
                throw new RuntimeException(response.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Request buildRequest(String url, String method, Object form) {
        long timestamp = System.currentTimeMillis();
        String content = JSON.toJSONString(form);
        String sign = sign(vipConfig.getPriKeyInPem(), timestamp + content);
        return new Request.Builder()
                .url(vipConfig.getHost() + url)
                .header("App-Code", vipConfig.getVipCode())
                .header("Timestamp", String.valueOf(timestamp))
                .header("Authorization", sign)
                .method(method, RequestBody.create(JSON_MEDIA_TYPE, content))
                .build();
    }

    private String sign(String priKey, String content) {
        try {
            PemReader pemReader = new PemReader(new StringReader(priKey));
            PemObject pemObject = pemReader.readPemObject();
            PKCS8EncodedKeySpec spec =
                    new PKCS8EncodedKeySpec(pemObject.getContent());
            KeyFactory kf = KeyFactory.getInstance("RSA");

            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) kf.generatePrivate(spec);
            RSADigestSigner signer = new RSADigestSigner(new SHA512Digest());
            signer.init(true, new RSAKeyParameters(true, rsaPrivateKey.getModulus(), rsaPrivateKey.getPrivateExponent()));
            byte[] data = content.getBytes();
            signer.update(data, 0, data.length);
            byte[] signature = signer.generateSignature();
            return Base16.encode(signature);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void newLock(AddLockForm addLockForm) {
        callRequest("/lock/new_lock", METHOD_PUT, addLockForm, new TypeReference<BaseResult<String>>() {
        }.getType());
    }

    public void resetKey(SetLockKeyForm setLockKeyForm) {
        callRequest("/lock/reset_key", METHOD_PUT, setLockKeyForm, new TypeReference<BaseResult<String>>() {
        }.getType());
    }

    public void resetName(SetLockNameForm setLockNameForm) {
        callRequest("/lock/set_name", METHOD_PUT, setLockNameForm, new TypeReference<BaseResult<String>>() {
        }.getType());
    }

    public void grantLockTo(GrantForm grantForm) {
        callRequest("/grant/grant_to", METHOD_PUT, grantForm, new TypeReference<BaseResult<String>>() {
        }.getType());
    }

    @Override
    public void batchGrantLockTo(BatchGrantForm grantForm) {
        callRequest("/grant/batch_grant_to", METHOD_PUT, grantForm, new TypeReference<BaseResult<String>>() {
        }.getType());
    }

    @Override
    public List<GrantVO> listGrant(LockForm lockForm) {
        return callRequest("/grant/list", METHOD_POST, lockForm, new TypeReference<BaseResult<List<GrantVO>>>() {
        }.getType());
    }

    public void removeGrant(RemoveGrantForm removeGrantForm) {
        callRequest("/grant/remove", METHOD_DELETE, removeGrantForm, new TypeReference<BaseResult<String>>() {
        }.getType());
    }

    @Override
    public void batchRemoveGrant(BatchRemoveGrantForm removeGrantForm) {
        callRequest("/grant/batch_remove", METHOD_DELETE, removeGrantForm, new TypeReference<BaseResult<String>>() {
        }.getType());
    }

    @Override
    public void addFingerPass(AddFingerPassForm addFingerPassForm) {
        callRequestWithoutBaseResult("/fingerpass/add", METHOD_POST, addFingerPassForm, new TypeReference<String>() {
        }.getType());
    }

    @Override
    public void deleteFingerPass(DeleteFingerPassForm deleteFingerPassForm) {
        callRequestWithoutBaseResult("/fingerpass/del", METHOD_POST, deleteFingerPassForm, new TypeReference<String>() {
        }.getType());
    }

    @Override
    public void setFingerPassword(SetPasswordForm setPasswordForm) {
        callRequestWithoutBaseResult("/fingerpass/set_password", METHOD_PUT, setPasswordForm, new TypeReference<String>() {
        }.getType());
    }

    @Override
    public void setFingerICCard(SetCardForm setCardForm) {
        callRequestWithoutBaseResult("/fingerpass/set_ic_card", METHOD_PUT, setCardForm, new TypeReference<String>() {
        }.getType());

    }

    @Override
    public void setFingerIDCard(SetCardForm setCardForm) {
        callRequestWithoutBaseResult("/fingerpass/set_id_card", METHOD_PUT, setCardForm, new TypeReference<String>() {
        }.getType());
    }

    @Override
    public void setFinger(SetTemplateForm setTemplateForm) {
        callRequestWithoutBaseResult("/fingerpass/set_finger", METHOD_PUT, setTemplateForm, new TypeReference<String>() {
        }.getType());
    }

    @Override
    public void setFace(SetTemplateForm setTemplateForm) {
        callRequestWithoutBaseResult("/fingerpass/set_face", METHOD_PUT, setTemplateForm, new TypeReference<String>() {
        }.getType());
    }

    @Override
    public boolean hasNeedSync(LockForm lockForm) {
        return callRequestWithoutBaseResult("/fingerpass/has_sync", METHOD_POST, lockForm, new TypeReference<Boolean>() {
        }.getType());
    }

    public List<FingerPassVO> listFingerPass(FingerPassForm lockForm) {
        return callRequestWithoutBaseResult("/fingerpass/list", METHOD_POST, lockForm, new TypeReference<List<FingerPassVO>>() {
        }.getType());
    }

    @Override
    public List<LogVO> findLog(ListLogForm listLogForm) {
        return callRequest("/log/list_by_date", METHOD_POST, listLogForm, new TypeReference<BaseResult<List<LogVO>>>() {
        }.getType());
    }

    public String sign(String content) {
        return this.sign(this.vipConfig.getPriKeyInPem(), content);
    }

    public LogVO getLogById(long id) {
        return this.callRequest("/log/get", "POST", new LogForm(id), (new TypeReference<BaseResult<LogVO>>() {
        }).getType());
    }

    @Override
    public void deleteLock(LockForm lockForm) {
        callRequest("/lock/", METHOD_DELETE, lockForm, new TypeReference<BaseResult<String>>() {
        }.getType());
    }

    @Override
    public String grantToApp(GrantLockToAppForm grantLockToAppForm) {
        String result = callRequest("/lock/grant_lock", METHOD_POST, grantLockToAppForm, new TypeReference<BaseResult<String>>() {
        }.getType());
        return result;
    }

    @Override
    public LockDetailVO getLockDetail(String id) {
        return this.callRequest("/lock/get", "POST", new LockForm(id), (new TypeReference<BaseResult<LockDetailVO>>() {
        }).getType());
    }
}
