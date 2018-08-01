package proy4.android.com.myresto.modelo;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("SpellCheckingInspection")
public class ProductoMenu implements Parcelable{

    private int id;
    private String nombre;
    private double precio;

    public ProductoMenu(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString(){
        return nombre + " - " + precio;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nombre);
        dest.writeDouble(this.precio);
    }

    protected ProductoMenu(Parcel in) {
        this.id = in.readInt();
        this.nombre = in.readString();
        this.precio = in.readDouble();
    }

    public static final Creator<ProductoMenu> CREATOR = new Creator<ProductoMenu>() {
        @Override
        public ProductoMenu createFromParcel(Parcel source) {
            return new ProductoMenu(source);
        }

        @Override
        public ProductoMenu[] newArray(int size) {
            return new ProductoMenu[size];
        }
    };
}
