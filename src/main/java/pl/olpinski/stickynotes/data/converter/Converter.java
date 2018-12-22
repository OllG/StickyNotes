package pl.olpinski.stickynotes.data.converter;

public interface Converter <O, DTO> {

    DTO convert (O object);
    O deconvert (DTO dtoObject);
}
