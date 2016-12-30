/**
 * 
 */
package com.prosnav.ivms.controller.helper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.prosnav.ivms.model.ModelType;
import com.prosnav.ivms.repository.ivm.FieldPicker;

/**
 * @author wuzixiu
 *
 */
public class Reference {
	public static final String KEY = "reference";
	
	private Map<String, Condiction> condictions;

	public Reference() {
		condictions = new HashMap<String, Condiction>();
	}

	public Reference with(ModelType mt, Set<Long> ids) {
		return with(mt, ids, null, "");
	}

	public Reference with(ModelType mt, Set<Long> ids, FieldPicker fp,
			String... fields) {
		Condiction condiction = ensureCondiction(mt, fp, fields);

		condiction.addAllIds(ids);
		return this;
	}

	public Reference with(ModelType mt, Long id, FieldPicker fp,
			String... fields) {
		Condiction condiction = ensureCondiction(mt, fp, fields);

		condiction.addId(id);
		return this;
	}

	public Reference with(ModelType mt, Long id) {
		Condiction condiction = ensureCondiction(mt, null, "");

		condiction.addId(id);
		return this;
	}

	public Reference with(int code, Long id, FieldPicker fp, String... fields) {
		ModelType mt = ModelType.ofCode(code);
		return with(mt, id, fp, fields);
	}

	public Reference with(int code, Set<Long> ids, FieldPicker fp,
			String... fields) {
		ModelType mt = ModelType.ofCode(code);
		return with(mt, ids, fp, fields);
	}

	public Reference with(int code, Long id) {
		ModelType mt = ModelType.ofCode(code);
		return with(mt, id);
	}

	public Reference with(int code, Set<Long> ids) {
		ModelType mt = ModelType.ofCode(code);
		return with(mt, ids);
	}

	private Condiction ensureCondiction(ModelType mt) {
		return ensureCondiction(mt, null, "");
	}

	private Condiction ensureCondiction(ModelType mt, FieldPicker fp,
			String... fields) {
		if (mt == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		sb.append(mt.getKey());

		if (fp != null) {
			sb.append(fp.name());

			for (String field : fields) {
				if (!StringUtils.isEmpty(field)) {
					sb.append(field);
				}
			}
		}

		String key = sb.toString();
		Condiction condiction = condictions.get(key);

		if (condiction == null) {
			condiction = new Condiction(mt, new HashSet<Long>(), fp, fields);
			condictions.put(key, condiction);
		}

		return condiction;
	}

	public Map<String, Condiction> getCondictions() {
		return condictions;
	}

	public static class Condiction {
		ModelType modelType;
		Set<Long> ids;
		FieldPicker fieldPicker;
		String[] fields;

		public Condiction(ModelType mt, Set<Long> ids, FieldPicker fp,
				String... fields) {
			this.modelType = mt;
			this.ids = ids;
			this.fieldPicker = fp;
			this.fields = fields;
		}

		public Condiction(ModelType mt, Set<Long> ids) {
			this.modelType = mt;
			this.ids = ids;
			this.fields = new String[] {};
		}

		public void addId(Long id) {
			ensureIdContainer();
			this.ids.add(id);
		}

		public void addAllIds(Set<Long> ids) {
			ensureIdContainer();
			this.ids.addAll(ids);
		}

		private void ensureIdContainer() {
			if (ids == null) {
				ids = new HashSet<Long>();
			}
		}

		public ModelType getModelType() {
			return modelType;
		}

		public Set<Long> getIds() {
			return ids;
		}

		public FieldPicker getFieldPicker() {
			return fieldPicker;
		}

		public String[] getFields() {
			return fields;
		}

		@Override
		public String toString() {
			return "Condiction [modelType=" + modelType + ", ids=" + ids
					+ ", fieldPicker=" + fieldPicker + ", fields="
					+ Arrays.toString(fields) + "]";
		}
	}

	@Override
	public String toString() {
		return "Reference [condictions=" + condictions + "]";
	}
	
}
