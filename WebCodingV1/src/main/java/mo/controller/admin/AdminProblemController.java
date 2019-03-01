package mo.controller.admin;

import mo.core.Result;

public interface AdminProblemController {
    Result problems(String page, String per_page);
}
