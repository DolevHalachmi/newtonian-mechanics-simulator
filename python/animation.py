import math
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
from matplotlib.patches import Rectangle
from matplotlib.transforms import Affine2D

CSV_PATH = "data.csv"

# ---- Load data ----
df = pd.read_csv(CSV_PATH)

t = df["time"].to_numpy()
s = df["position"].to_numpy()
v = df["velocity"].to_numpy()
ke = df["kinetic_energy"].to_numpy()

a0 = float(df["acceleration"].iloc[0])
fricF0 = float(df["friction_force"].iloc[0])
totalF0 = float(df["total_force"].iloc[0])
theta = float(df["theta"].iloc[0])  # radians

# ---- Slope geometry ----
slope_len = max(1.0, float(s.max()))
cos = math.cos(theta)
sin = math.sin(theta)

x_len = slope_len * cos
y_len = slope_len * sin

# ---- Precompute block centers along the slope ----
# s=0 at (x_len, y_len) and increases toward (0,0)
x_centers = x_len - s * cos
y_centers = y_len - s * sin

# ---- Figure ----
fig, ax = plt.subplots()
ax.set_aspect("equal", adjustable="box")

# slope line
ax.plot([0, x_len], [0, y_len], linewidth=2, zorder=1)

pad = 0.2 * slope_len
ax.set_xlim(-pad, x_len + pad)
ax.set_ylim(-pad, y_len + pad)

ax.set_title("Newtonian Mechanics Simulator", pad=18)
ax.set_xlabel("x")
ax.set_ylabel("y")

# ---- Rectangle (block) ----
block_w = 0.08 * slope_len
block_h = 0.05 * slope_len

# define rectangle at origin; we will position + rotate it each frame
block = Rectangle((0, 0), block_w, block_h, facecolor="red", edgecolor="black", zorder=3)
ax.add_patch(block)

# ---- Text ----
values_text = ax.text(
    0.02, 0.98, "",
    transform=ax.transAxes,
    ha="left", va="top",
    fontsize=9,
    bbox=dict(boxstyle="round,pad=0.3", facecolor="white", alpha=0.7, edgecolor="none"),
    zorder=4
)

def set_block_pose(cx: float, cy: float):
    # place block so that (cx, cy) is its center
    block.set_xy((cx - block_w / 2.0, cy - block_h / 2.0))

    # rotate around the block center in *data coords*
    tr = Affine2D().rotate_around(cx, cy, theta) + ax.transData
    block.set_transform(tr)

def init():
    # initialize at frame 0
    set_block_pose(float(x_centers[0]), float(y_centers[0]))
    values_text.set_text("")
    return block, values_text

def update(i: int):
    cx = float(x_centers[i])
    cy = float(y_centers[i])
    set_block_pose(cx, cy)

    values_text.set_text(
        f"t = {t[i]:.2f} s\n"
        f"v = {v[i]:.2f} m/s\n"
        f"a = {a0:.2f} m/s²\n"
        f"KE = {ke[i]:.2f} J\n"
        f"F_fric = {fricF0:.2f} N\n"
        f"F_total = {totalF0:.2f} N"
    )
    return block, values_text

interval_ms = 16
ani = FuncAnimation(
    fig,
    update,
    frames=len(t),
    init_func=init,
    interval=interval_ms,
    blit=True,
    cache_frame_data=False
)

plt.show()