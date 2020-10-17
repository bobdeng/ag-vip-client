package com.anguokeji.smartlock.vipclient;

import com.anguokeji.smartlock.vipclient.forms.*;

import java.util.List;

public interface VipClient {

    void newLock(AddLockForm addLockForm);

    void resetKey(SetLockKeyForm setLockKeyForm);

    void resetName(SetLockNameForm setLockNameForm);

    void grantLockTo(GrantForm grantForm);

    void removeGrant(RemoveGrantForm removeGrantForm);

    void batchGrantLockTo(BatchGrantForm grantForm);

    List<GrantVO> listGrant(LockForm lockForm);

    void deleteLock(LockForm lockForm);

    void batchRemoveGrant(BatchRemoveGrantForm removeGrantForm);

    void addFingerPass(AddFingerPassForm addFingerPassForm);

    void deleteFingerPass(DeleteFingerPassForm deleteFingerPassForm);

    void setFingerPassword(SetPasswordForm setPasswordForm);

    void setFingerICCard(SetCardForm setCardForm);

    void setFingerIDCard(SetCardForm setCardForm);

    void setFinger(SetTemplateForm setTemplateForm);

    void setFace(SetTemplateForm setTemplateForm);

    boolean hasNeedSync(LockForm lockForm);

    List<FingerPassVO> listFingerPass(FingerPassForm lockForm);

    String sign(String content);

    LogVO getLogById(long id);

    List<LogVO> findLog(ListLogForm listLogForm);

    LockDetailVO getLockDetail(String id);

    String grantToApp(GrantLockToAppForm grantLockToAppForm);
}
