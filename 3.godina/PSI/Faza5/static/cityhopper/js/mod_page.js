// Authors : Ilija Obradovic 0049/2020
var townNum = 0
$(document).ready(
    function(){
        var modal = document.getElementById("myModal");
        //modal.style.display = "block";
        $("#erase0").toggle();
        function updateDivs(cls){
            let divs = $("."+cls);
            let i = 0;
            divs.each(function(){
                $(this).find(".loc_"+cls+"_lbl").find("h4").text("Road " + (++i) + ":")
            });
        }
        function getNewDiv(){
            let div = $("#closest_town0").clone();


            let eraseBtn = div.find("#erase0");
            let c_loc = div.find("#id_cloc0");
            let c_loc_dist = div.find("#id_dist_cloc0")
            let townNum = $(".town").length;
            div.attr("id", "closest_town" + townNum)
                .find(".loc_town_lbl").find("h4")
                .text("Road " + (townNum + 1) + ":");


            eraseBtn.attr({
                id: "erase_" + townNum
            }).toggle();

            c_loc.attr({
                id: "id_cloc_" + townNum,
                name: "cloc" + townNum,
            })

            c_loc_dist.attr({
                id: "id_dist_cloc_" + townNum,
                name: "dist_cloc" + townNum
            }).val("");

            eraseBtn.click(function(){
                $(this).parent().parent().remove();
                updateDivs("town");
            });

            $("#towns_div").append(div);

        }




        // function showEditModal(id){
        //     $.ajax({
        //         url: "get_town/"+id,
        //         method: "GET",
        //         success: function(data){
        //             document.getElementById("myModal").innerHTML=data
        //             $("#myModal").toggle()
        //             $(".close").click(function(){
        //                 $("#myModal").toggle();
        //                 $("body").attr("class", "")
        //             })
        //             $("#new_town_more").click(getNewEditDiv);
        //             $("body").attr("class", "modal-open")
        //         }
        //     })
        //                 // .fail(function() {
        //                 //     alert( "Failed to read data about town!" );
        //                 // })
        // }


        function updateVisibility(){
            let sr_text = $("#sr_loc").val();
            let a = $(".town_row");
            a.each(function(){
                let el = $(this);
                let rt = el.find("h5").text();
                if(rt.toLowerCase().indexOf(sr_text.toLowerCase()) === -1)
                    el.hide();
                else
                    el.show();

            })
        }

        $("#town_more").click(getNewDiv);

        setTimeout(function(){
            $("#msg_data").fadeOut(10000);
        }, 1000)


        $("#sr_loc").on("input", updateVisibility);
        //$("#sr_loc").on("delete", updateVisibility)
    }
);