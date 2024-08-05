// Authors : Ilija Obradovic 0049/2020
$(document).ready(function() {
    function updateDivs(cls) {
        let divs = $("." + cls);
        let i = 0;
        divs.each(function () {
            $(this).find(".loc_" + cls + "_lbl").find("h4").text("Road " + (++i) + ":")
        });
    }

    function update(data) {
        let div = $("<div></div>").html(data).find("#closest_town0").clone();


        let eraseBtn = div.find("#erase0");
        let c_loc = div.find("#id_cloc0");
        let c_loc_dist = div.find("#id_dist_cloc0")
        let townNum = $(".road").length;
        div.find(".loc_town_lbl").attr("class", "loc_road_lbl");

        div.attr("class", "road");

        div.attr("id", "road_" + townNum)
            .find(".loc_road_lbl").find("h4")
            .text("Road " + (townNum + 1) + ":");

        eraseBtn.attr({
            id: "new_erase_" + townNum
        });

        c_loc.attr({
            id: "id_new_cloc_" + townNum,
            name: "new_road" + townNum,
        })

        c_loc_dist.attr({
            id: "id_new_dist_cloc_" + townNum,
            name: "dist_new_road" + townNum
        }).val("");

        eraseBtn.click(function () {
            $(this).parent().parent().remove();
            updateDivs("road");
        });
        $("#edit_towns_div").append(div);
    }
    setTimeout(function(){
            $("#msg_data").fadeOut(10000);
        }, 1000)
    function getNewEditDiv() {
        $.ajax({
            url: "/cityhopper/moderator_add",
            method: "GET",
            success: function (data){
                update(data)
            }
        })

    }

    $("#new_town_more").click(getNewEditDiv)
})