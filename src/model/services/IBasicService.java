package model.services;

import java.lang.reflect.InvocationTargetException;

public interface IBasicService<T extends BasicService> {

	static <T extends BasicService> T getService(Class<T> serviceClass) {
		try {
			return (T) serviceClass.getDeclaredConstructor().newInstance();

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException ex) {
			throw new IllegalStateException(ex.getMessage());
		}
	}
}
