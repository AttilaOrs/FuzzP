package structure;

public interface IResolver<Tsource, Tdestination> {

  Tdestination resolve(Tsource source);

}
