pragma solidity ^0.8.2;

contract Contract {
    address payable owner;
    address payable courier=payable(address (0));
    address customer;

    bool paid=false;
    bool transit=false;
    bool delivered=false;

    constructor (address cust){
        customer=cust;
        owner=payable(msg.sender);
    }

    function is_paid() external view returns (bool){
        return paid;
    }
    function in_transit() external view returns (bool){
        return transit;
    }
    function is_delivered() external view returns (bool){
        return delivered;
    }
    function is_customer(address addr) external returns (bool){
        return customer==addr;
    }

    function pay() external payable {
        paid=true;
    }
    function pick_up_order(address payable c) external {
        courier=c;
        transit=true;
    }
    function deliver() external {
		owner.transfer(address(this).balance * 80 / 100);
		courier.transfer(address(this).balance * 20 / 100);
        delivered=true;
    }
}