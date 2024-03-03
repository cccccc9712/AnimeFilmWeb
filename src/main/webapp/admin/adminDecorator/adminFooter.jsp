<%--
  Created by IntelliJ IDEA.
  User: tmtmt
  Date: 2/29/2024
  Time: 1:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    body {
        display: flex;
        flex-direction: column;
        min-height: 100vh;
    }
    .footer {
        background-color: #2b2b31; /* Đổi màu nền của footer thành #2b2b31 */
    }
    .footer .text-muted {
        color: white; /* Đổi màu chữ trong footer thành màu trắng */
    }
    .wrapper {
        flex: 1;
        margin-bottom: 10px;
    }
    .footer {
        flex-shrink: 0;
    }
</style>

<footer class="footer mt-auto py-3">
    <div class="container text-center">
        <span class="text-muted">© 2024 Henix - Anime Film Web</span>
    </div>
</footer>


