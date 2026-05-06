import streamlit as st
import requests

st.set_page_config(page_title="Hệ thống Bán Hàng", layout="wide")
st.title("🛍️ Quản lý User - Kết nối Spring Boot")

# Nút bấm để gọi API Get All Users
if st.button("🔄 Tải danh sách người dùng"):
    try:
        # Gọi đến API Spring Boot của bạn (cổng 8080)
        # Nhớ là Backend Java phải đang bấm RUN nhé!
        response = requests.get("http://localhost:8080/users")
        
        if response.status_code == 200:
            data = response.json()
            st.success("✅ Kết nối Backend thành công!")
            # Hiển thị kết quả từ ApiResponse.result (đúng chuẩn code Java của bạn)
            st.table(data['result']) 
        else:
            st.error(f"❌ Lỗi: Server Java trả về mã {response.status_code}")
    except Exception as e:
        st.error(f"🔌 Không thể kết nối! Hãy chắc chắn bạn đã nhấn RUN dự án Java trong IntelliJ.")

st.divider()
st.caption("Giao diện được xây dựng bằng Streamlit để test API Spring Boot.")
st.divider()
st.subheader("🛠️ Cập nhật thông tin User")

with st.form("update_form"):
    u_id = st.text_input("Nhập ID User cần sửa")
    u_name = st.text_input("Tên mới")
    u_pass = st.text_input("Mật khẩu mới", type="password")
    submit = st.form_submit_button("Xác nhận Cập nhật")

    if submit:
        payload = {
            "password": u_pass,
            "firstName": u_name # Map đúng với field trong UserUpdateRequest của bạn
        }
        # Gọi API PUT mà bạn đã viết ở UserController
        res = requests.put(f"http://localhost:8080/users/{u_id}", json=payload)
        if res.status_code == 200:
            st.success("Đã cập nhật! MapStruct MappingTarget đã hoạt động.")
        else:
            st.error("Cập nhật thất bại. Kiểm tra lại ID!")