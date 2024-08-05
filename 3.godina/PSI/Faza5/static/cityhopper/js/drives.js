// Authors : Ana Zoric 0009/2020

$(document).ready(
    /**
     * Function which hides all dropdowns on page load
     */
    function(){
        var u=document.getElementById("user_drop");
        var d=document.getElementById("driver_drop");
        var p=document.getElementById("passenger_drop");
        var m=document.getElementById("moderator_drop");
        if (u) u.hidden=true;
        if (d) d.hidden=true;
        if (p) p.hidden=true;
        if (m) m.hidden=true;
    }
);

/**
 * Function hides all dropdowns except the specified one
 * @param {String}  id   id of dropdown which is supposed to appear
 */
function toggle_drop(id){

    var u=document.getElementById("user_drop");
    var d=document.getElementById("driver_drop");
    var p=document.getElementById("passenger_drop");
    var m=document.getElementById("moderator_drop");
    if (u) u.hidden=true;
    if (d) d.hidden=true;
    if (p) p.hidden=true;
    if (m) m.hidden=true;
    switch (id){
        case 1: //user
            if (u) u.hidden=false;
            break;
        case 2: //driver
            if (d) d.hidden=false;
            break;
        case 3: //moderator
            if (m) m.hidden=false;
            break;
        case 4: //passenger
            if (p) p.hidden=false;
            break;
    }
}

/**
 * Function shows the button group specified
 * @param {Integer} id  Identifier of button group that is supposed to be shown
 */
function show_buttons(id){
    var el=document.getElementById(id)
    el.hidden=false
}

/**
 * Function hides the button group specified
 * @param {Integer} id  Identifier of button group that is supposed to be hidden
 */
function hide_buttons(id){
    var el=document.getElementById(id)
    el.hidden=true
}

/**
 * Function toggles the visibility the button group specified
 * @param {Integer} id  Identifier of button group that is supposed to be toggled
 */
function toggle_buttons(id){
    var el=document.getElementById(id);
    if(el.hidden) show_buttons(id);
    else hide_buttons(id);
}

/**
 * Function clears create_drive form fields
 */
function clear_create_drive_form(){
    f=document.getElementById("cd_form")
    for (var i=0;i<6;i++){
        f.elements[i].value="";
    }
}

/**
 *Function shows next 3 comments on comments page
 * @param {Integer} n    number of comments
 */
function fill_comments(n){
    document.getElementById("fin_prev").disabled=false;
    var ind=[]
    for (let i=0;i<n;i++) {
        if (!document.getElementById("comment" + i).hidden) {
            ind.push(i)
        }
        document.getElementById("comment" + i).hidden = true;
    }
    console.log(ind)
    if (ind.length==0) ind=[-3,-2,-1]
    for(let i=0;i<3 && ind[i]+3<n;i++){
        document.getElementById("comment" + (ind[i]+3)).hidden = false;
        if (ind[i]+3==n-1) document.getElementById("fin_comments").disabled=true;
        if (ind[i]+3==0) document.getElementById("fin_prev").disabled=true;
    }
    document.getElementById("fin_comments").innerText="Next";
    document.getElementById("fin_prev").hidden=false;
}

/**
 *Function shows next 3 comments on drivers profile
 * @param {Integer} n    number of comments
 */
function fill_comments_profile(n){
    document.getElementById("prof_prev").disabled=false;
    var ind=[]
    for (let i=0;i<n;i++) {
        if (!document.getElementById("commentt" + i).hidden) {
            ind.push(i)
        }
        document.getElementById("commentt" + i).hidden = true;
    }
    console.log(ind)
    if (ind.length==0) ind=[-3,-2,-1]
    for(let i=0;i<3 && ind[i]+3<n;i++){
        document.getElementById("commentt" + (ind[i]+3)).hidden = false;
        if (ind[i]+3==n-1) document.getElementById("prof_comments").disabled=true;
        if (ind[i]+3==0) document.getElementById("prof_prev").disabled=true;
    }
    document.getElementById("prof_comments").innerText="Next";
    document.getElementById("prof_prev").hidden=false;
}

/**
 *Function shows 3 previous comments on comments page
 * @param {Integer} n    number of comments
 */
function prev_comments(n){
    document.getElementById("fin_comments").disabled=false;
     var ind=[]
    for (let i=0;i<n;i++) {
        if (!document.getElementById("comment" + i).hidden) {
            ind.push(i)
        }
        document.getElementById("comment" + i).hidden = true;
    }
    if (ind.length==0) ind=[3,4,5]
    while (ind.length<3){
        ind.push(ind[ind.length-1]+1)
    }
    console.log(ind);
    for(let i=0;i<3 && ind[i]-3<n;i++){
        document.getElementById("comment" + (ind[i]-3)).hidden = false;
        if (ind[i]-3==0) document.getElementById("fin_prev").disabled=true;
        if (ind[i]-3==n-1) document.getElementById("fin_comments").disabled=true;
    }
}

/**
 *Function shows 3 previous comments on drivers profile
 * @param {Integer} n    number of comments
 */
function prev_comments_profile(n){
    document.getElementById("prof_comments").disabled=false;
     var ind=[]
    for (let i=0;i<n;i++) {
        if (!document.getElementById("commentt" + i).hidden) {
            ind.push(i)
        }
        document.getElementById("commentt" + i).hidden = true;
    }
    if (ind.length==0) ind=[3,4,5]
    while (ind.length<3){
        ind.push(ind[ind.length-1]+1)
    }
    console.log(ind);
    for(let i=0;i<3 && ind[i]-3<n;i++){
        document.getElementById("commentt" + (ind[i]-3)).hidden = false;
        if (ind[i]-3==0) document.getElementById("prof_prev").disabled=true;
        if (ind[i]-3==n-1) document.getElementById("prof_comments").disabled=true;
    }
}