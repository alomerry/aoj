package mo.controller.admin;

import mo.core.Result;

public interface AdminUserController {
    Result users(String page, String per_page);
}
