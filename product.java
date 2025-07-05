/*
  Author : Ahmed Karam
*/
public class product
{
String name;
int price;
int quantity_instock;
Boolean expires;
Boolean shipping;
int weight;

public product(String name, int price, int quantity_instock, boolean expires, boolean shipping,int weight) 
{
    this.name = name;
    this.price = price;
    this.quantity_instock = quantity_instock;
    this.expires = expires;
    this.shipping = shipping;
    if(shipping) this.weight = weight;
}

public product(String name, int price, int quantity_instock) 
{
    this(name, price, quantity_instock, false, false,0);
}

}