//
// Handler for home page

document.addEventListener('DOMContentLoaded', () => {

  // Radar configuration
  const config = {
    svg_id: "radar",
    width: 1450,
    height: 1000,
    colors: {
      background: "#fff",
      grid: "#bbb",
      inactive: "#ddd"
    }
  }
  console.log('radar configuration is:', config)

  const svg_rings = [
    { radius: 130 },
    { radius: 220 },
    { radius: 310 },
    { radius: 400 }
  ];
  console.log('svg_rings is:', svg_rings)

  const svg_segments = [
    { radial_min: 0, radial_max: 0.5, factor_x: 1, factor_y: 1 },
    { radial_min: 0.5, radial_max: 1, factor_x: -1, factor_y: 1 },
    { radial_min: -1, radial_max: -0.5, factor_x: -1, factor_y: -1 },
    { radial_min: -0.5, radial_max: 0, factor_x: 1, factor_y: -1 }
  ];
  console.log('svg_segments is:', svg_rings)

  /**
   * WTF?
   */
  function translate(x, y) {
    return "translate(" + x + "," + y + ")";
  }

  // Extract radar configuration
  const radar_name = d3.selectAll("span#name").text();
  const radar_description = d3.selectAll("span#description").text();
  console.log('radar name is:', radar_name)
  console.log('radar description is:', radar_description)

  // Extract all segments
  const html_segments = d3.selectAll("table#segments tbody tr");
  const segments = Array.from(html_segments._groups[0]).map(function (segment, j) {
    const children = Array.from(segment.children);
    return {
      title: children[0].innerHTML,
      desciption: children[1].innerHTML
    }
  });
  console.log('segments are:', segments)

  // Extract all rings
  const html_rings = d3.selectAll("table#rings tbody tr");
  const rings = Array.from(html_rings._groups[0]).map(function (ring, j) {
    const children = Array.from(ring.children);
    return {
      title: children[0].innerHTML,
      desciption: children[1].innerHTML,
      priority: children[2].innerHTML
    }
  });
  console.log('rings are:', rings)

  // Extract all blips
  const html_blips = d3.selectAll("table#blips tbody tr");
  const blips = Array.from(html_blips._groups[0]).map(function (blip, j) {
    const children = Array.from(blip.children);
    return {
      title: children[0].innerHTML,
      ring: children[1].innerHTML,
      segment: children[2].innerHTML,
      description: children[3].innerHTML
    }
  });
  console.log('blips are:', blips)

  // Build svg
  const svg = d3.select("svg#" + config.svg_id)
    .style("background-color", config.colors.background)
    .attr("width", config.width)
    .attr("height", config.height);

  // Build radar
  const radar = svg.append("g");
  radar.attr("transform", translate(config.width / 2, config.height / 2));

  // Draw grid lines
  const grid = radar.append("g");
  grid.append("line")
    .attr("x1", 0).attr("y1", -400)
    .attr("x2", 0).attr("y2", 400)
    .style("stroke", config.colors.grid)
    .style("stroke-width", 1);
  grid.append("line")
    .attr("x1", -400).attr("y1", 0)
    .attr("x2", 400).attr("y2", 0)
    .style("stroke", config.colors.grid)
    .style("stroke-width", 1);

  // Background color. Usage `.attr("filter", "url(#solid)")`
  // SOURCE: https://stackoverflow.com/a/31013492/2609980
  const defs = grid.append("defs");
  const filter = defs.append("filter")
    .attr("x", 0)
    .attr("y", 0)
    .attr("width", 1)
    .attr("height", 1)
    .attr("id", "solid");
  filter.append("feFlood")
    .attr("flood-color", "rgb(0, 0, 0, 0.8)");
  filter.append("feComposite")
    .attr("in", "SourceGraphic");

  // Draw rings
  for (let i = 0; i < svg_rings.length; i++) {
    grid.append("circle")
      .attr("cx", 0)
      .attr("cy", 0)
      .attr("r", svg_rings[i].radius)
      .style("fill", "none")
      .style("stroke", config.colors.grid)
      .style("stroke-width", 1);
  }

  // Setup layer for entries
  const rink = radar.append("g")
    .attr("id", "rink");

  // Rollover bubble (on top of everything else)
  const bubble = radar.append("g")
    .attr("id", "bubble")
    .attr("x", 0)
    .attr("y", 0)
    .style("opacity", 0)
    .style("pointer-events", "none")
    .style("user-select", "none");
  bubble.append("rect")
    .attr("rx", 4)
    .attr("ry", 4)
    .style("fill", "#333");
  bubble.append("text")
    .style("font-family", "sans-serif")
    .style("font-size", "10px")
    .style("fill", "#fff");
  bubble.append("path")
    .attr("d", "M 0,0 10,0 5,8 z")
    .style("fill", "#333");

  // Draw blips on radar
  /*
  const blips = rink.selectAll(".blip")
    .data(config.entries)
    .enter()
    .append("g")
    .attr("class", "blip")
    .attr("transform", function(d, i) { return legend_transform(d.quadrant, d.ring, i); })
    .on("mouseover", function(d) { showBubble(d); highlightLegendItem(d); })
    .on("mouseout", function(d) { hideBubble(d); unhighlightLegendItem(d); });
   */



});