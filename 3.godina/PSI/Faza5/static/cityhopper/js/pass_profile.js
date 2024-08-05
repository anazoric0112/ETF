// Authors : Ilija Obradovic 0049/2020
var fix = 1;
$(document).ready(function(){
        $(".close").click(function(){
            let p = $(this).parent().parent()
            p.find('.update_btn_res').show()
            p.find('.new_seats_data').hide()
        })
        $(".update_btn_res").click(function(){
            let p = $(this).parent().parent()
            p.find('.new_seats_data').show()
            $(this).hide()
        })
        let full = "../../static/cityhopper/imgs/star_full.png"
        let empty = "../../static/cityhopper/imgs/star_empty.png"
        $(".star").hover(function(){

            let id = parseInt($(this).attr("ind"))
            for(let i = 1; i <= id; i++)
                $(this).parent().find( '.star[ind="' + i + '"]').attr("src", full)
            for(let i = id+1; i <= 5; i++)
                $(this).parent().find('.star[ind="' + i + '"]').attr("src", empty)
        })
        $(".star").click(function(){
            $(this).parent().parent().parent().find('input[name="rat"]').attr("value",parseInt($(this).attr("ind")));
        })

        $(".stars").mouseout(function(){
            let t = $(this).parent().parent().find('input[name="rat"]').attr("value")
            $(this).find(".star").each(function(){
                if(parseInt($(this).attr("ind")) <= t)
                    $(this).attr("src", full)
                else
                    $(this).attr("src", empty)
            })
        })

        setTimeout(function(){
            $("#msg_data").fadeOut(2000);
        }, 1000)
    }
)