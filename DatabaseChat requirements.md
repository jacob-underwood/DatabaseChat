
**Authentication screen**

- *Login screen*
	- User can sign in
		- Error handling (sign in error?)
- *Register screen*
	- User can create an account
		- Error handling (unique username?)

**Main View**

- *Create chat room*
	- Named
	- *Saved history*
		- Error handling (duplicate name?)
	- *Load history*
- *Join chat room*
	- Multiple users at once
		- Error handling (does it exist?)
- *Change username and password*
	- Error handling (duplicate name? same password?)
- *Log out*

**Chat Room View**

- *Commands*
	- /list
	- /leave
	- /history
	- /help
		- Error handling (invalid command?)
- Regular chats
- Constantly updating


**Users**

- Username
- Password
- User ID


**DatabaseAccessor**

- SQL
- *Stores and loads usernames and passwords*
- *Stores and loads chat room history*
