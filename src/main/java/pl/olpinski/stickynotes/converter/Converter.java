package pl.olpinski.stickynotes.converter;

public interface Converter <O, DTO> {

    DTO convert (O object);
    O deconvert (DTO dtoObject);
}
