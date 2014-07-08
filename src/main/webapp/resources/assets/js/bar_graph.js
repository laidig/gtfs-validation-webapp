function BarChart(title)
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
			labels: labels,
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
			highlighter:{
			    show:true,
			    tooltipAxes: 'y',
			    formatString:'<div class="graph_tooltip" style="border: 1px solid black">TEST: %s</div>'
			},
		      pointLabels: {show: true}
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
					angle: -30,
				},
				ticks: Dates
			},
			yaxis: {
				label: 'Trips per Depot',
				labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
				autoscale:true,
			}
		}
	};
        return optionsObj;
	}
  

   
