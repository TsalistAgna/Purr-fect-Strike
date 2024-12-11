public class Key {
    private boolean kanan;
    private boolean kiri;
    private boolean spasi;
    private boolean key_j;
    private boolean key_enter;

    public boolean isKanan(){
        return kanan;
    }

    public void setKanan(boolean kanan){
        this.kanan=kanan;
    }

    public boolean isKiri(){
        return kiri;
    }

    public void setKiri(boolean kiri){
        this.kiri=kiri;
    }

    public boolean isSpasi(){
        return spasi;
    }

    public void setSpasi(boolean spasi){
        this.spasi=spasi;
    }

    public boolean isKey_j(){
        return key_j;
    }

    public void setKey_j(boolean key_j){
        this.key_j=key_j;
    }

    public boolean isKey_enter(){
        return key_enter;
    }

    public void setKey_enter(boolean key_enter){
        this.key_enter = key_enter;
    }

}
