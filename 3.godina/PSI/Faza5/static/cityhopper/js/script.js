// Author : Savo Cvijetic 0054/2020
function init()
{
//    $(".user").mousedown(function(){
//        $(".sub-menu-wrap").toggle();
//    });
    $(document).mouseup(function(e)
    {
        var user = $(".user");
        var smw = $(".sub-menu-wrap");
        if(user.is(e.target) || user.has(e.target).length != 0)
        {
            smw.toggle();
            return;
        }
        if (!smw.is(e.target) && smw.has(e.target).length === 0)
        {
            smw.hide();
        }
    });
}