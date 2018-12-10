package jdbc;


public class KhachHang {
	private int id;
	private String name;
	private String address;
	private Float salary;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Float getSalary() {
		return salary;
	}
	public void setSalary(Float salary) {
		this.salary = salary;
	}
	public KhachHang() {
		super();
		// TODO Auto-generated constructor stub
	}
	public KhachHang(int id, String name, String address, Float salary) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "KhachHang [id=" + id + ", name=" + name + ", address=" + address + ", salary=" + salary + "]";
	}
	
}
