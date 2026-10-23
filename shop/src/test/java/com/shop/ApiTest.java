package com.shop;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest @AutoConfigureMockMvc
class ApiTest {
  @Autowired MockMvc mvc;

  MockHttpSession login(String u, String p) throws Exception {
    var s = new MockHttpSession();
    mvc.perform(post("/api/login").session(s).contentType(MediaType.APPLICATION_JSON)
        .content("{\"username\":\"" + u + "\",\"password\":\"" + p + "\"}")).andExpect(status().isOk());
    return s;
  }
  @Test void wrongPasswordRejected() throws Exception {
    mvc.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
        .content("{\"username\":\"admin\",\"password\":\"sai\"}")).andExpect(status().isUnauthorized());
  }
  @Test void guestCannotListUsers() throws Exception { mvc.perform(get("/api/users")).andExpect(status().isUnauthorized()); }
  @Test void customerCannotListUsers() throws Exception {
    mvc.perform(get("/api/users").session(login("khach", "khach123"))).andExpect(status().isForbidden());
  }
  @Test void adminCanListUsers() throws Exception {
    mvc.perform(get("/api/users").session(login("admin", "admin123"))).andExpect(status().isOk());
  }
  @Test void customerCannotAddProduct() throws Exception {
    mvc.perform(post("/api/products").session(login("khach", "khach123")).contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"X\",\"category\":\"Y\",\"price\":1,\"stock\":1}")).andExpect(status().isForbidden());
  }
  @Test void invalidProductRejected() throws Exception {
    mvc.perform(post("/api/products").session(login("nhanvien", "nv123456")).contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"X\",\"category\":\"Y\",\"price\":-5,\"stock\":1}")).andExpect(status().isBadRequest());
  }
  @Test void emptyCartRejected() throws Exception {
    mvc.perform(post("/api/orders").session(login("khach", "khach123")).contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"A\",\"phone\":\"0912345678\",\"address\":\"Thái Nguyên\",\"payment\":\"COD\",\"items\":[]}"))
        .andExpect(status().isBadRequest());
  }
}
