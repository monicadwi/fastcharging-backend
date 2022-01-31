package id.go.bppt.ptik.fastcharging.dbapi.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OutputWrapper {
	private List<?> datas;
	private int number;
	private int size;
	private int numberOfElements;
	private int totalPage;
	private Long totalElements;
}
