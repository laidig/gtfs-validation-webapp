function BarChart(title,Labels,Dates,Depots)
  {
	var optionsObj = {		
		title: title,
		stackSeries: true,
		legend: {
			show: true,
			renderer: $.jqplot.EnhancedLegendRenderer,
			rendererOptions: {
				numberRows: 1
			},
			placement: 'outsideGrid',
			labels: Labels,
			location: 's'
		},
		animate: true,
	    seriesDefaults:{
		      renderer:$.jqplot.BarRenderer,
		      rendererOptions: {
				    animation: {
					speed: 2500
				    },
				    barWidth: 15,
				    highlightMouseOver: true
		      },
		      highlighter: {
		  		sizeAdjust: 10,
		  		show: true,
		          tooltipLocation: 'n',
		          tooltipAxes: 'y',
		  		tooltipOffset:+10,
		  		yvalue:2,
		  		formatString:'<span class="graph_tooltip" style="border: 1px solid black"><span style="display:none;">%s</span>Trip Count: %s</span>',
		          useAxesFormatters: false
		  		}
		    },
	        highlighter:{
		    show:true,
		},
	        cursor:{
		    show:false
		},
		axesDefaults: {
			tickRenderer: $.jqplot.CanvasAxisTickRenderer,
		},
		axes: {
			xaxis: {
				renderer: $.jqplot.CategoryAxisRenderer,
				tickOptions: {
					angle: -90,
				},
				ticks: Dates
			},
			yaxis: {
	            tickRenderer:$.jqplot.CanvasAxisTickRenderer,
	            labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
				tickOptions:{
	                labelPosition: 'middle',
	                angle:-30
	            },
				labelOptions:{
	                fontFamily:'Helvetica',
	                fontSize: '14pt'
	            },
				label: 'Trips per Depot'
			}
		}
	};
        return optionsObj;
	}
  

   
