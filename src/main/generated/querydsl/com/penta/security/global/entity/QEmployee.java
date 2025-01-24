package com.penta.security.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmployee is a Querydsl query type for Employee
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmployee extends EntityPathBase<Employee> {

    private static final long serialVersionUID = 1291610951L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmployee employee = new QEmployee("employee");

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final ListPath<DeptEmp, QDeptEmp> deptEmps = this.<DeptEmp, QDeptEmp>createList("deptEmps", DeptEmp.class, QDeptEmp.class, PathInits.DIRECT2);

    public final ListPath<DeptManager, QDeptManager> deptManagers = this.<DeptManager, QDeptManager>createList("deptManagers", DeptManager.class, QDeptManager.class, PathInits.DIRECT2);

    public final QEmployeeDoc employeeDoc;

    public final QEmployeeName employeeName;

    public final NumberPath<Integer> empNo = createNumber("empNo", Integer.class);

    public final StringPath firstName = createString("firstName");

    public final EnumPath<Employee.Gender> gender = createEnum("gender", Employee.Gender.class);

    public final DatePath<java.time.LocalDate> hireDate = createDate("hireDate", java.time.LocalDate.class);

    public final StringPath lastName = createString("lastName");

    public final ListPath<Salary, QSalary> salaries = this.<Salary, QSalary>createList("salaries", Salary.class, QSalary.class, PathInits.DIRECT2);

    public final ListPath<Title, QTitle> titles = this.<Title, QTitle>createList("titles", Title.class, QTitle.class, PathInits.DIRECT2);

    public QEmployee(String variable) {
        this(Employee.class, forVariable(variable), INITS);
    }

    public QEmployee(Path<? extends Employee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmployee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmployee(PathMetadata metadata, PathInits inits) {
        this(Employee.class, metadata, inits);
    }

    public QEmployee(Class<? extends Employee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employeeDoc = inits.isInitialized("employeeDoc") ? new QEmployeeDoc(forProperty("employeeDoc"), inits.get("employeeDoc")) : null;
        this.employeeName = inits.isInitialized("employeeName") ? new QEmployeeName(forProperty("employeeName"), inits.get("employeeName")) : null;
    }

}

