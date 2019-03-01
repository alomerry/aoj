package mo.controller.advice;

/*
@ControllerAdvice
public class GobelException {

    @ExceptionHandler(PermissionDeniedException.class)
    public void permissionDeniedException(HttpServletRequest request, HttpServletResponse response, PermissionDeniedException exception) {
        try {
            request.setAttribute(StringValue.error_msg, StringValue.Exception_PermissionDenied);
            request.setAttribute(StringValue.error_reason, exception.getReason());
            request.getRequestDispatcher("/404").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @ExceptionHandler(Exception.class)
    public void exception(HttpServletRequest request, HttpServletResponse response, Exception e) {
        try {
            request.setAttribute(StringValue.error_msg, StringValue.Exception_Unknown);
            request.setAttribute(StringValue.error_reason, e.getCause().getMessage());
            request.getRequestDispatcher("/404").forward(request, response);
        } catch (ServletException | IOException ee) {
            e.printStackTrace();
        }
    }
}*/
