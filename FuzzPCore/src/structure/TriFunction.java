package structure;

import java.util.function.Function;

@FunctionalInterface
public interface TriFunction<T, U, S, R> {

  R apply(T t, U u, S s);
  
  default <W> TriFunction<T, U, S, W> andThen(Function<? super R, ? extends W> after) {
    if(after == null) {
      throw new RuntimeException(" after shoud vbe not null");
    }
    return (T t, U u, S v) -> after.apply(apply(t, u, v));
  }
 
}