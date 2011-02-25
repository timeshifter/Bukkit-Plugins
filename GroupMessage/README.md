# Group Message

Send messages to entire groups!

## Permissions

* `groupmessage.send` - allows usage of the `/mg` and `/mgt` commands.
* `groupmessage.omnipotent` - all group messages will be shown to users with this permission.

# Commands

* `/mg <group> <msg>` - send a message to all players in `<group>`. The given `<group>` may be
  as few letters as you wish; `M` and `m` will both match the group `Moderator`, for example.
* `/mgt <group> [msg]` - toggle a group as your default send-to. Any standard you chat after
  toggling will be sent the group you specifiy. To clear your default send-to run `/mgt` with
  no arguments.
