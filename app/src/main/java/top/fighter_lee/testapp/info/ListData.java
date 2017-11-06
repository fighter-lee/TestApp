package top.fighter_lee.testapp.info;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ListData<T> implements Serializable {

	@SerializedName("result")
	public List<T> list;

}
