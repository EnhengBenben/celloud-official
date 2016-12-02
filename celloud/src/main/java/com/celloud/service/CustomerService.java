package com.celloud.service;

import com.celloud.model.mongo.UserCaptcha;

public interface CustomerService {

    public Boolean addOrUpdateUserCaptcha(String cellphone, String captcha);

    public UserCaptcha getUserCaptchaByCellphone(String cellphone);

    public void removeUserCaptchaByCellphone(String cellphone);

}
