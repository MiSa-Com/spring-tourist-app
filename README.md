<h1>Project base for Java web HIT 2022</h1>

<h2>I. Các package</h2>
<h3>1. adapter: cho web, app...</h3>
<p>- Chứa các base và các version của ứng dụng</p>
<p>1.1: base: chứa các annotation, class, interface base cho toàn bộ version</p>
<p>1.2: v1: Chứa code điều kiển hoặc đầu vào của version 1, có thể phát triển thêm nhiều version</p>
<p>1.3: transfer -> chứa các parameter, response</p>

<h3>2. application</h3>
<p>Chứa các class, interface như: annotation, constants, repository, input, output, service, mapper, util...</p>

<h3>3. config</h3>
<p>Chứa các config của ứng dụng, exception...</p>

<h3>4. domain</h3>
<p>Chứa các class dto, entity, object trong entity, dto...</p>

<h2>II. Resources</h2>
<h3>1. config</h3>
<p>- Config connect database...</p>
<p>- Config mybatis</p>

<h3>2. i18n</h3>
<p>- Chứa file message.properties config nội dung message của cả ứng dụng</p>

<h3>3. static</h3>
<p>- Chứa các file tĩnh: simple data, css, js</p>

<h3>4. templates</h3>
<p>- Chứa các file code như html....</p>

<h3>5. repository (nếu có)</h3>
<p>- Dùng để chứa các file mapper với mybatis...</p>

<h2>III. Luồng hoạt động</h2>
<p>Bước 1: Định nghĩa entity, liên kết bảng...</p>
<p>Bước 2: Tạo các repository cho các entity</p>
<p>Bước 3: Tạo parameter, body cho từng endpoint</p>
<p>Bước 4: Tạo class input cho endpoint, được map từ parameter</p>
<p>Bước 5: Tạo class output cho endpoint, hứng output của service</p>
<p>Bước 6: Tạo class service, xử lý logic tại đây. Đầu vào là class input, đầu ra là class output</p>
<p>Bước 7. Tạo class controller, nhận parameter, body... Biến đổi parameter -> input => service => output -> return 
output</p>

<h2>IV. Lưu ý để dễ bảo trì</h2>
<p>- Tạo class constant định nghĩa các quá trị String, number để tránh hardcode</p>
<p>- Các message định nghĩa trong file application để tránh phải sửa nhiều chỗ</p>
<p>- Tạo các annotation validate param</p>
<p>- Đoạn nào xử lý logic nhiều class dùng lại thì cho vào util</p>
<p>- Dùng map struct để map các class</p>

<br/>
<b>====> Đây là project viết theo ý cá nhân, nên tham khảo. Tránh chê :v</b>