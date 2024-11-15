package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.example.domain.Employee;

// @Author:金丸天
// Employeeのリポジトリクラス

@Repository
public class EmployeeRepository {

    // @param
    // id=id
    // name=名前
    // image=画像
    // gender=性別
    // hireDate=誕生日
    // mailAddress=メールアドレス
    // zipCode=郵便番号
    // address=住所
    // telephone=電話番号
    // salary=給与
    // characteristics=特製
    // dependentsCount=扶養人数
    // return DBからレコードごとにオブジェクトを生成したリストを返す
    private static final RowMapper<Employee> ROW_MAPPER = (rs, i) -> {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setImage(rs.getString("image"));
        employee.setGender(rs.getString("gender"));
        employee.setHireDate(rs.getDate("hire_date"));
        employee.setMailAddress(rs.getString("mail_address"));
        employee.setZipCode(rs.getString("zip_code"));
        employee.setAddress(rs.getString("address"));
        employee.setTelephone(rs.getString("telephone"));
        employee.setSalary(rs.getInt("salary"));
        employee.setCharacteristics(rs.getString("characteristics"));
        employee.setDepaendentsCount(rs.getInt("dependents_count"));
        return employee;

    };

    // template:DBから検索してきたオブジェクトが渡される
    @Autowired
    private NamedParameterJdbcTemplate template;

    // DB上にemployeeのオブジェクトを全て返す
    public List<Employee> findAll() {

        String sql = "SELECT * FROM employees";

        return template.query(sql, ROW_MAPPER);

    }

    // idでemployeeのオブジェクトを1件返す
    public Employee load(Integer id) {

        String sql = "SELECT * FROM employees WHERE id = :id";

        SqlParameterSource source = new MapSqlParameterSource("id", id);

        return template.queryForObject(sql, source, ROW_MAPPER);

    }

    // employeeのオブジェクトをDBに登録する
    public void update(Employee employee) {

        String sql = "UPDATE employees SET name = :name, image = :image, gender = :gender, hire_date = :hireDate, mail_address = :mailAddress, zip_code = :zipCode, address = :address, telephone = :telephone, salary = :salary, characteristics = :characteristics, dependents_count = :dependentsCount WHERE id = :id";

        SqlParameterSource source = new BeanPropertySqlParameterSource(employee);

        template.update(sql, source);

    }

}
